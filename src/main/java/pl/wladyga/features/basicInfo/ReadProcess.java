package pl.wladyga.features.basicInfo;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.Instant;

@NoArgsConstructor(staticName = "of")
@Getter
public class ReadProcess {

    private StringBuilder build = new StringBuilder();

    public Long countProcesses(){
        return ProcessHandle.allProcesses()
                .filter(process -> process.info().command().isPresent())
                .count();
    }

    public String readProcess() {
        ProcessHandle.allProcesses()
                .filter(process -> process.info().command().isPresent())
                .forEach(this::processDetails);

        return build.toString();
    }

    private void processDetails(ProcessHandle process) {
        build.append("Owner: %s%n").append(process.info().user().orElse(""));
        build.append("Start time: %s%n").append(process.info().startInstant().orElse(Instant.now()).toString());
        build.append("Command: %s%n").append(process.info().command().orElse(""));
        build.append("\n");
    }
}
