import java.util.Scanner;

public class Main {
	static Scanner read = new Scanner(System.in);

	public static void main(String[] args) {
		System.out.println("How many devices?");
		int noDev = read.nextInt();
		CPU mainCPU = new CPU(0);
		boolean running = true;
		Device[] devArray = new Device[noDev];

		for (int i = 0; i < noDev; i++) {
			devArray[i] = new Device(i + 1);
		}

		/* Set initial running process to 0 */
		String inputT;
		int ID = 0;
		int process = 0;
		System.out.println("Interrupt List:\n\t0t: Ungraceful Shutdown\n\t15: Timer Interrupt\n\t64: Wait");
		System.out
				.println("\t65: ID of current process\n\t70: Request I/O\n\t71: Complete I/O\n\t80: Create a process");
		System.out.println("\t81: Terminate a process\n\t244: Graceful shutdown\n\t25: Display current status");
		System.out.println("-------------------------------");

		while (running == true) {
			System.out.println("-------------------------------");
			System.out.println("Please enter an interrupt");
			
			inputT = read.next();
			
			switch (inputT) {
			case "0t":
				System.out.println("Program terminated with exit code 0t");
				running = false;
				break;
			case "15":
				interrupt(mainCPU);
				break;
			case "64":
				waitCall(mainCPU);
				break;
			case "65":
				//System.out.print("ID: ");
				ID = read.nextInt();
				signal(mainCPU, ID);
				break;
			case "70":
				//System.out.print("Device #: ");
				ID = read.nextInt();
				requestIO(mainCPU, devArray[ID - 1]);
				break;
			case "71":
				//System.out.print("Device #: ");
				ID = read.nextInt();
				completeIO(mainCPU, devArray[ID - 1]);
				break;
			case "80":
				process++;
				mainCPU.create(process);
				break;
			case "81":
				destroy(mainCPU);
				break;
			case "244":
				goodbye(devArray, mainCPU);
				running = false;
				break;
			case "255":
				display(devArray, mainCPU);
				break;
			default:
				System.out.println("Invalid token");
				break;
			}
		}
	}

	/* Service the timer interrupt */
	public static void interrupt(CPU cpu) {
		cpu.interrupt();
	}

	/* Service the wait system call interrupt */
	public static void waitCall(CPU cpu) {
		cpu.waitCall();
	}

	/* Service the Signal Process interrupt */
	public static void signal(CPU cpu, int ID) {
		cpu.pidCall(ID);
	}

	/* Request I/O on DevID */
	public static void requestIO(CPU cpu, Device DevID) {
		cpu.setCurrent();
		DevID.addProc(cpu.getCurrent());
		DevID.setCurrentProc();
		cpu.reqIO();
	}

	/* Complete I/O on DevID */
	public static void completeIO(CPU cpu, Device DevID) {
		cpu.completed(DevID.getCurrentProc());
		DevID.completeIO();

	}

	/* Creates a process */
	public static void create(CPU cpu, int ID) {
		cpu.create(ID);
	}

	/* Terminates a Process */
	public static void destroy(CPU cpu) {
		cpu.setCurrent();
		cpu.destroy();
	}

	/* Method to shut down gracefully */
	public static void goodbye(Device[] arr, CPU cpu) {
		for (int i = 0; i < arr.length; i++) {
			arr[i].shutDown();
		}
		cpu.shutDown();
		System.out.println("All proccesses terminated gracefully.");
	}

	/* Method to display status */
	public static void display(Device[] arr, CPU cpu) {
		System.out.println("============================");
		//if (arr.length != 0) {
		for (int i = 0; i < arr.length  ; i++) {
			if (arr[i].getList().size() != 0) {
				System.out.print(arr[i].toString());
				System.out.println("============================");
			}
		//}
		}
		System.out.print(cpu.toString());
		System.out.println("============================ ");
	}
}
