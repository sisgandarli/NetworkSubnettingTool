import java.util.ArrayList;

public class NetworkSubnetter {
    private String initialAddress;

    private String address;
    private int prefix;
    private int hostsBits;
    private String networkAddressInBinary;
    private int prevNeworkAddressInDecimal;

    private String subnetMask;
    private ArrayList<Network> networks;
    private ArrayList<Subnet> subnets;

    public NetworkSubnetter(String initialAddress, ArrayList<Network> networks) {
        this.initialAddress = initialAddress;
        this.networks = networks;
        this.subnets = new ArrayList<>();
    }

    private void splitStuff() {
        String[] splitted = initialAddress.split("\\/");
        this.address = splitted[0];
        this.prefix = Integer.parseInt(splitted[1]);
        this.hostsBits = 32 - prefix;
        this.networkAddressInBinary = "";
        this.subnetMask = "";

        for (int i = 0; i < 32; i++) {
            subnetMask = i < prefix ? subnetMask + "1" : subnetMask + "0";
        }


        for (String i : splitted[0].split("\\.")) {
            String num = String.format("%8s", Integer.toBinaryString(Integer.parseInt(i))).replace(" ", "0");
            networkAddressInBinary += num;
        }
        String tmp = "";
        for (int i = 0; i < networkAddressInBinary.length(); i++) {
            char a = networkAddressInBinary.charAt(i);
            char b = subnetMask.charAt(i);
            if (a == '1' && a == b) {
                tmp += "1";
            } else {
                tmp += "0";
            }
        }
        this.networkAddressInBinary = tmp;
        this.prevNeworkAddressInDecimal = (int) Long.parseLong(networkAddressInBinary, 2);
    }

    private int getPowerOfTwo(int num) {
        return (int) Math.ceil(Math.log(num) / Math.log(2));
    }

    public ArrayList<Subnet> createSubnets() {
        splitStuff();
        for (Network i : networks) {
            Subnet subnet = new Subnet();

            int left = prevNeworkAddressInDecimal;
            int powerOfTwo = getPowerOfTwo(i.getNumberOfHosts() + 2);
            int right = left + (int) (Math.pow(2, powerOfTwo));

            subnet.setNetworkName(i.getNetworkName());
            subnet.setPrefix(32 - powerOfTwo);
            subnet.setNetworkAddress(Integer.toBinaryString(left));
            subnet.setLeftBoundary(Integer.toBinaryString(left));
            subnet.setRightBoundary(Integer.toBinaryString(right - 1));
            subnet.setBroadcastAddress(Integer.toBinaryString(right - 1));
            subnets.add(subnet);
            subnet.makeThingsPrettier();

            prevNeworkAddressInDecimal = right;
        }

        return subnets;
    }
}
