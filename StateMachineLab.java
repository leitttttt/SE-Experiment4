public class StateMachineLab {

    // ==========================================
    // B1: 恒温水箱状态机
    // ==========================================
    enum WaterTankState { DETECTING, HEATING, REPAIRING, SLEEPING }
    
    public static void simulateWaterTank() {
        System.out.println("\n--- B1: 恒温水箱状态机模拟 ---");
        WaterTankState currentState = WaterTankState.DETECTING;
        double temperature = 15.0; 
        boolean hasWater = true;  
        
        if (currentState == WaterTankState.DETECTING) {
            if (temperature < 20 && hasWater) {
                currentState = WaterTankState.HEATING;
                System.out.println("状态切换: 温度<20°C且有水 -> 【烧水状态】");
            } else if (temperature < 20 && !hasWater) {
                System.out.println("状态保持: 温度<20°C但无水 -> 不打开电源，保持【检测状态】");
            }
        }
        
        if (currentState == WaterTankState.HEATING) {
            if (temperature >= 100) {
                currentState = WaterTankState.DETECTING;
                System.out.println("状态切换: 温度达到100°C -> 断开电源，返回【检测状态】");
            }
        }
        System.out.println("当前水箱状态: " + currentState);
    }

    // ==========================================
    // B2: ControlPanel 控制面板状态机
    // ==========================================
    enum PanelState { READING, VERIFYING, LOCKED, FUNCTION_SELECT }
    
    public static void simulateControlPanel() {
        System.out.println("\n--- B2: ControlPanel 状态机模拟 ---");
        PanelState currentState = PanelState.READING;
        int inputLength = 0;
        int errorCount = 0;
        
        inputLength = 4; 
        System.out.println("户主敲击键盘，当前状态: 【" + currentState + "】");
        
        if (inputLength == 4) {
            currentState = PanelState.VERIFYING;
            System.out.println("字符长度达到4位 -> 进入【" + currentState + "】");
        }
        
        boolean isPasswordCorrect = false;
        if (currentState == PanelState.VERIFYING) {
            if (isPasswordCorrect) {
                currentState = PanelState.FUNCTION_SELECT;
                System.out.println("密码正确 -> 进入【系统功能选择状态】");
            } else {
                errorCount++;
                if (errorCount >= 3) {
                    currentState = PanelState.LOCKED;
                    System.out.println("密码错误且次数=3 -> 进入【锁定状态】");
                } else {
                    currentState = PanelState.READING;
                    System.out.println("密码错误且次数<3 -> 返回【读取键入字符状态】");
                }
            }
        }
        System.out.println("当前面板状态: " + currentState);
    }

    // ==========================================
    // B3: 源代码字符串提取状态机
    // ==========================================
    public static void extractStrings(String sourceCode) {
        System.out.println("\n--- B3: 源代码字符串提取 ---");
        boolean inString = false;    
        boolean isEscaped = false;   
        StringBuilder currentString = new StringBuilder();

        for (int i = 0; i < sourceCode.length(); i++) {
            char c = sourceCode.charAt(i);

            if (inString) {
                if (isEscaped) {
                    if (c == '"' || c == '\\') {
                        currentString.append(c);
                    } else {
                        currentString.append('\\').append(c); 
                    }
                    isEscaped = false;
                } else if (c == '\\') {
                    isEscaped = true; 
                } else if (c == '"') {
                    inString = false; 
                    System.out.println("提取到字符串: " + currentString.toString());
                    currentString.setLength(0);
                } else {
                    currentString.append(c);
                }
            } else {
                if (c == '"') {
                    inString = true; 
                }
            }
        }
    }

    public static void main(String[] args) {
        simulateWaterTank();
        simulateControlPanel();
        
        String testCode = "\"一般学习一门编程语言的时候，基本上第一个程序是输出\\\"Hello World!\\\"\"";
        extractStrings(testCode);
    }
}