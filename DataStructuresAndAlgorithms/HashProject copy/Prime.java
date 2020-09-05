import java.util.ArrayList;
import java.util.Random;

public class Prime {
	Random rnd = new Random();
	ArrayList<Integer> arr = new ArrayList<Integer>();
	
	int lower = 95500;
	int higher = 96000;
	
	public int modularExponation(int n) {
		int base = rnd.nextInt(n) + 1;
		int result = base;
		char[] binary = Integer.toBinaryString(n-1).toCharArray();
		for (int i = 1; i < binary.length; i++) {
			if (binary[i] == '1') {
				result = (int) ((Math.pow(result, 2) * base) % n);
			}else {
				result = (int) ((Math.pow(result, 2)) % n);
			}
		}
		return result;
	}
	
	public int getPrime() {
		int result = -1;
		
		for (int i = lower; i < higher; i++) {
			for (int j = 0; j < 3; j++) {
				result = modularExponation(i);
			}
			
			if (result == 1) {
				if (arr.size() !=0) {
					if(i - arr.get(0) == 2) {
						return i;
					}else {
						arr.remove(0);
						arr.add(i);
					}
				}else {
					arr.add(i);
				}
			}
		}
		return -1;
	}
}
