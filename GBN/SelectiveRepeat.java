import java.util.Random;

public class SelectiveRepeat {
    private int windowSize;
    private int totalPackets;
    private boolean[] sentPackets;
    private boolean[] ackedPackets;

    public SelectiveRepeat(int windowSize, int totalPackets) {
        this.windowSize = windowSize;
        this.totalPackets = totalPackets;
        this.sentPackets = new boolean[totalPackets];
        this.ackedPackets = new boolean[totalPackets];
    }

    private boolean sendPacket(int seqNum) {
        System.out.println("Sending packet " + seqNum);
        // Simulate packet loss
        if (new Random().nextDouble() < 0.2) { // 20% chance of loss
            System.out.println("Packet " + seqNum + " lost!");
            return false;
        }
        return true;
    }

    private void receiveAck(int ackNum) {
        System.out.println("Received ACK for packet " + ackNum);
        ackedPackets[ackNum] = true;
    }

    public void simulate() {
        int base = 0;

        while (base < totalPackets) {
            // Send packets in the window
            for (int i = base; i < base + windowSize && i < totalPackets; i++) {
                if (!sentPackets[i]) {
                    if (sendPacket(i)) {
                        sentPackets[i] = true;
                    }
                }
            }

            // Simulate ACK reception or timeout
            try {
                Thread.sleep(1000); // Simulate delay
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            if (new Random().nextDouble() < 0.8) { // 80% chance of ACK
                int ackNum = new Random().nextInt(Math.min(base + windowSize, totalPackets) - base) + base;
                receiveAck(ackNum);
            }

            // Slide the window
            while (base < totalPackets && ackedPackets[base]) {
                base++;
            }
        }
        System.out.println("All packets sent and acknowledged!");
    }

    public static void main(String[] args) {
        SelectiveRepeat sr = new SelectiveRepeat(4, 10);
        sr.simulate();
    }
}
