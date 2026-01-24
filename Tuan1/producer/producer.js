const express = require("express");
const amqp = require("amqplib");

const app = express();
app.use(express.json());

const RABBITMQ_URL = process.env.RABBITMQ_URL || "amqp://user:password@rabbitmq:5672";
const EVENT_QUEUE = process.env.EVENT_QUEUE || "service_events";

let channel;

async function connectRabbitMQ() {
  while (true) {
    try {
      const connection = await amqp.connect(RABBITMQ_URL);
      channel = await connection.createChannel();
      await channel.assertQueue(EVENT_QUEUE, { durable: true });
      console.log("Service 1 connected to RabbitMQ");
      break;
    } catch (err) {
      console.log("Waiting for RabbitMQ...", err.message);
      await new Promise((resolve) => setTimeout(resolve, 3000));
    }
  }
}

async function ensureChannel() {
  if (channel) return channel;
  await connectRabbitMQ();
  return channel;
}

app.post("/events", async (req, res) => {
  const { type, payload } = req.body || {};

  if (!type) {
    return res.status(400).json({ error: "type is required" });
  }

  const event = {
    type,
    payload: payload || {},
    producedAt: new Date().toISOString(),
    producer: "service-1"
  };

  const chan = await ensureChannel();
  chan.sendToQueue(EVENT_QUEUE, Buffer.from(JSON.stringify(event)), { persistent: true });

  console.log("Service 1 pushed event", event);
  res.json({ status: "queued", queue: EVENT_QUEUE, event });
});

app.post("/send", async (req, res) => {
  const { message } = req.body || {};

  if (!message) {
    return res.status(400).json({ error: "message is required" });
  }

  const event = {
    type: "message.sent",
    payload: { message },
    producedAt: new Date().toISOString(),
    producer: "service-1"
  };

  const chan = await ensureChannel();
  chan.sendToQueue(EVENT_QUEUE, Buffer.from(JSON.stringify(event)), { persistent: true });

  console.log("Service 1 pushed event", event);
  res.json({ status: "sent", dataSent: event });
});

connectRabbitMQ();

app.listen(3000, () => {
  console.log("Service 1 API listening on port 3000");
});
