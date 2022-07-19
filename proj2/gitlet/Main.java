package gitlet;

public class Main {

    /** Usage: java gitlet.Main ARGS, where ARGS contains
     *  <COMMAND> <OPERAND1> <OPERAND2> ... 
     */
    public static void main(String[] args) {
        if (args.length == 0) {
            exitWithError("Please enter a command.");
        }
        String firstArg = args[0];
        switch(firstArg) {
            case "init":
                initialInputCheck(0,args);
                if (Repository.GITLET_DIR.exists()) {
                    exitWithError("A Gitlet version-control system already exists in the current directory.");
                }
                Repository.setupPersistence();
                Repository.init();
                break;
            case "add":
                // TODO: RM -> ADD test, change -> ADD -> change to previous commit state -> ADD test,
                // TODO: ADD unchanged from previous commit test
                initialInputCheck(1,args);
                String fileName = args[1];
                if (!Utils.join(Repository.CWD, fileName).exists()) {
                    exitWithError("File does not exist.");
                }
                Repository.add(fileName);
                break;
            case "commit":
                initialInputCheck(1,args);
                String message = args[1];
                if (message == null || message.equals("")) {
                    exitWithError("Please enter a commit message.");
                }
                if (Repository.stagingArea.isEmpty()) {
                    exitWithError("No changes added to the commit.");
                }
                Repository.commit(message);
                break;
            default:
                exitWithError("No command with that name exists.");
        }
    }

    public static void exitWithError(String message) {
        if (message != null && !message.equals("")) {
            System.out.println(message);
        }
        System.exit(0);
    }

    public static void initialInputCheck(int properNumArgs, String[] args) {
        if (args.length - 1 != properNumArgs) {
            exitWithError("Incorrect operands.");
        } else if (!args[0].equals("init") && !Repository.GITLET_DIR.exists()) {
            exitWithError("Not in an initialized Gitlet directory.");
        }
    }
}
