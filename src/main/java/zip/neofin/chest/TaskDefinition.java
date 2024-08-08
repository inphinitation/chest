package zip.neofin.chest;

import java.util.List;

interface TaskDefinition {
    boolean isComplex();
    List<Task> breakdown();
    Object execute();
}