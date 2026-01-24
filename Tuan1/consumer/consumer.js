const amqp = require("amqplib");

const RABBITMQ_URL = process.env.RABBITMQ_URL || "amqp://user:password@rabbitmq:5672";
const EVENT_QUEUE = process.env.EVENT_QUEUE || "service_events";

async function connectWithRetry() {
  try {
    console.log("Service 2 connecting...");
    const connection = await amqp.connect(RABBITMQ_URL);
    const channel = await connection.createChannel();

    await channel.assertQueue(EVENT_QUEUE, { durable: true });
    console.log("Service 2 waiting for events...");

    channel.consume(
      EVENT_QUEUE,
      async (msg) => {
        if (!msg) return;

        const raw = msg.content.toString();
        console.log("Service 2 received raw:", raw);

        try {
          const event = JSON.parse(raw);
          await handleEvent(event);
          channel.ack(msg);
        } catch (err) {
          console.log("Service 2 failed to process message, acking to skip:", err.message);
          channel.ack(msg);
        }
      },
      { noAck: false }
    );
  } catch (err) {
    console.log("Service 2 retry in 3s...", err.message);
    setTimeout(connectWithRetry, 3000);
  }
}

async function handleEvent(event) {
  // Simulate async work. Replace with real business logic.
  console.log("Handling event", event.type || "unknown", "from", event.producer || "unknown");
  await new Promise((resolve) => setTimeout(resolve, 500));
}

connectWithRetry();
