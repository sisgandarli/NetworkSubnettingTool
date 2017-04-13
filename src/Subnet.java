public class Subnet {
    private String networkName;
    private String networkAddress;
    private String leftBoundary;
    private String rightBoundary;
    private String broadcastAddress;
    private int prefix;

    private String networkAddressPretty;
    private String leftBoundaryPretty;
    private String rightBoundaryPretty;
    private String broadcastAddressPretty;

    private String pretty(String s) {
        String res = "";
        for (int i = 0; i < 32; i += 8) {
            res += Integer.parseInt(s.substring(i, i + 8), 2);
            if (i < 24) {
                res += ".";
            } else {
                res += "/";
            }
        }
        res += prefix;
        return res;
    }

    public void makeThingsPrettier() {
        this.networkAddressPretty = pretty(networkAddress);
        this.leftBoundaryPretty = pretty(leftBoundary);
        this.rightBoundaryPretty = pretty(rightBoundary);
        this.broadcastAddressPretty = pretty(broadcastAddress);
    }

    public int getPrefix() {
        return prefix;
    }

    public void setPrefix(int prefix) {
        this.prefix = prefix;
    }

    public String getNetworkName() {
        return networkName;
    }

    public void setNetworkName(String networkName) {
        this.networkName = networkName;
    }

    public String getNetworkAddress() {
        return networkAddress;
    }

    public void setNetworkAddress(String networkAddress) {
        this.networkAddress = networkAddress;
    }

    public String getLeftBoundary() {
        return leftBoundary;
    }

    public void setLeftBoundary(String leftBoundary) {
        this.leftBoundary = leftBoundary;
    }

    public String getRightBoundary() {
        return rightBoundary;
    }

    public void setRightBoundary(String rightBoundary) {
        this.rightBoundary = rightBoundary;
    }

    public String getBroadcastAddress() {
        return broadcastAddress;
    }

    public void setBroadcastAddress(String broadcastAddress) {
        this.broadcastAddress = broadcastAddress;
    }

    public String getNetworkAddressPretty() {
        return networkAddressPretty;
    }

    public void setNetworkAddressPretty(String networkAddressPretty) {
        this.networkAddressPretty = networkAddressPretty;
    }

    public String getLeftBoundaryPretty() {
        return leftBoundaryPretty;
    }

    public void setLeftBoundaryPretty(String leftBoundaryPretty) {
        this.leftBoundaryPretty = leftBoundaryPretty;
    }

    public String getRightBoundaryPretty() {
        return rightBoundaryPretty;
    }

    public void setRightBoundaryPretty(String rightBoundaryPretty) {
        this.rightBoundaryPretty = rightBoundaryPretty;
    }

    public String getBroadcastAddressPretty() {
        return broadcastAddressPretty;
    }

    public void setBroadcastAddressPretty(String broadcastAddressPretty) {
        this.broadcastAddressPretty = broadcastAddressPretty;
    }

    @Override
    public String toString() {
        return networkName + " " + leftBoundaryPretty + " " + rightBoundaryPretty;
    }
}
