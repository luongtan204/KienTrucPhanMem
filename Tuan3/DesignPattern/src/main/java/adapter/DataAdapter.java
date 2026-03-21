package adapter;

public class DataAdapter implements JSONService {
    private XMLSystem xmlSystem;

    public DataAdapter(XMLSystem xmlSystem) {
        this.xmlSystem = xmlSystem;
    }

    @Override
    public String getJSON() {
        String xml = xmlSystem.getXML();
        return convertXMLToJSON(xml);
    }

    private String convertXMLToJSON(String xml) {
        // Simple XML to JSON conversion logic
        // Remove XML declaration
        xml = xml.replaceAll("<\\?xml[^>]*\\?>", "").trim();

        // Convert XML tags to JSON format
        StringBuilder json = new StringBuilder();
        json.append("{");

        // Extract tag content and convert
        String[] lines = xml.split(">");
        for (String line : lines) {
            line = line.trim();
            if (line.isEmpty() || line.startsWith("</")) {
                continue;
            }

            // Extract tag name and content
            if (line.startsWith("<")) {
                String tagName = line.substring(1).split("\\s|>")[0];
                String content = line.substring(line.indexOf(">") + 1);

                if (!content.isEmpty()) {
                    if (json.length() > 1) {
                        json.append(",");
                    }
                    json.append("\"").append(tagName).append("\":\"").append(content).append("\"");
                }
            }
        }

        json.append("}");
        return json.toString();
    }
}

