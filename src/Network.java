public class Network {
    private String networkName;
    private int numberOfHosts;

    public Network() {

    }

    public Network(String networkName, int numberOfHosts) {
        this.networkName = networkName;
        this.numberOfHosts = numberOfHosts;
    }

    public String getNetworkName() {
        return networkName;
    }

    public void setNetworkName(String networkName) {
        this.networkName = networkName;
    }

    public int getNumberOfHosts() {
        return numberOfHosts;
    }

    public void setNumberOfHosts(int numberOfHosts) {
        this.numberOfHosts = numberOfHosts;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Network network = (Network) o;
        if (numberOfHosts != network.numberOfHosts) return false;
        return networkName.equals(network.networkName);
    }

    @Override
    public int hashCode() {
        int result = networkName.hashCode();
        result = 31 * result + numberOfHosts;
        return result;
    }

    @Override
    public String toString() {
        return String.format("%s : %d", networkName, numberOfHosts);
    }
}
