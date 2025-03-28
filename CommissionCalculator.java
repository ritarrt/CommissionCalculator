import java.util.Scanner;

public class CommissionCalculator {

    // ฟังก์ชันคำนวณอัตราค่านายหน้าเบี้ยประกันปีแรก
    public static double getCommissionRate(int policyAge, int customerAge) {
        if (customerAge < 0 || customerAge > 60) {
            return 0.0; // ไม่มีค่านายหน้าสำหรับอายุนอกช่วง 0-60
        }

        switch (policyAge) {
            case 1:
                return 2.0; // ทุกช่วงอายุ 0-60 ได้ 2%
            case 2:
                if (customerAge <= 50) {
                    return 1.0; // อายุ 0-50 ได้ 1%
                } else {
                    return 2.0; // อายุ 51-60 ได้ 2%
                }
            case 3:
                return 1.0; // ทุกช่วงอายุ 0-60 ได้ 1%
            default:
                return 0.0;
        }
    }

    // ฟังก์ชันคำนวณอัตราค่าจัดงานต่อเบี้ยประกันภัย
    public static double getOverridingRate(int customerAge, boolean isAnnual) {
        if (customerAge >= 0 && customerAge <= 50) {
            // หากอายุอยู่ระหว่าง 0-50 ปี
            return isAnnual ? 20.0 : 16.0; // อัตราสำหรับกรมธรรม์ Annual และ Non-Annual
        } else if (customerAge >= 51 && customerAge <= 60) {
            // หากอายุอยู่ระหว่าง 51-60 ปี
            return 13.0; // อัตราค่าจัดงานคงที่ 13% ไม่ขึ้นอยู่กับประเภทกรมธรรม์
        }
        return 0.0; // หากอายุเกิน 60 ปี หรือไม่ตรงเงื่อนไข
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean continueCalculation = true;

        while (continueCalculation) {
            // รับข้อมูลจากผู้ใช้
            System.out.print("กรุณากรอกอายุกรมธรรม์ (ปี): ");
            int policyAge = scanner.nextInt();
            
            // ตรวจสอบข้อมูลอายุกรมธรรม์
            if (policyAge < 1) {
                System.out.println("อายุกรมธรรม์ต้องไม่น้อยกว่า 1 ปี");
                continue;
            }

            System.out.print("กรุณากรอกอายุของผู้เอาประกัน: ");
            int customerAge = scanner.nextInt();

            // สำหรับการคำนวณประเภทกรมธรรม์
            double commissionRate = getCommissionRate(policyAge, customerAge);
            double overridingRate = 0.0;
            
            // หากอายุอยู่ในช่วง 51-60 ปี จะใช้ Overriding Rate คงที่ 13% และไม่ต้องถามประเภทกรมธรรม์
            if (customerAge >= 51 && customerAge <= 60) {
                overridingRate = getOverridingRate(customerAge, false); // ไม่สนใจประเภทกรมธรรม์
            } else {
                System.out.print("กรุณากรอกประเภทกรมธรรม์ (1 สำหรับ Annual, 2 สำหรับ Non-Annual): ");
                int policyType = scanner.nextInt();
                boolean isAnnual = (policyType == 1);
                overridingRate = getOverridingRate(customerAge, isAnnual);
            }

            System.out.println("อัตราค่านายหน้า (Commission Rate): " + commissionRate + "%");
            System.out.println("อัตราค่าจัดงาน (Overriding Rate): " + overridingRate + "%");

            // ถามผู้ใช้ว่าต้องการคำนวณต่อหรือไม่
            System.out.print("ต้องการคำนวณต่อหรือไม่? (y/n): ");
            String answer = scanner.next();

            if (answer.equalsIgnoreCase("n")) {
                continueCalculation = false;
                System.out.println("จบการทำงานของโปรแกรม");
            }
        }

        scanner.close();
    }
}
