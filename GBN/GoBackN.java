import java.util.Random;

public class GoBackN {
    private int windowSize;
    private int base;
    private int nextSeqNum;
    private int totalPackets;

    public GoBackN(int windowSize, int totalPackets) {
        this.windowSize = windowSize;
        this.base = 0;
        this.nextSeqNum = 0;
        this.totalPackets = totalPackets;
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
        base = ackNum + 1;
    }

    private void simulateTimeout() {
        System.out.println("Timeout! Resending packets from " + base);
        nextSeqNum = base;
    }

    public void simulate() {
        while (base < totalPackets) {
            // Send packets within the window
            while (nextSeqNum < base + windowSize && nextSeqNum < totalPackets) {
                if (sendPacket(nextSeqNum)) {
                    nextSeqNum++;
                }
            }

            // Simulate ACK reception or timeout
            try {
                Thread.sleep(1000); // Simulate delay
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            if (new Random().nextDouble() < 0.8) { // 80% chance of ACK
                    int ackNum = new Random().nextInt(nextSeqNum - base) + base;
                receiveAck(ackNum);
            } else {
                simulateTimeout();
            }
        }
        System.out.println("All packets sent and acknowledged!");
    }

    public static void main(String[] args) {
        GoBackN gbn = new GoBackN(4, 10);
        gbn.simulate();
    }
}
