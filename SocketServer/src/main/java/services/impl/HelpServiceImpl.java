package services.impl;

import dto.HelpDto;
import dto.Type;
import lombok.NoArgsConstructor;
import services.interfaces.HelpService;

import java.util.Arrays;
import java.util.List;

@NoArgsConstructor
public class HelpServiceImpl implements HelpService {
    @Override
    public HelpDto help() {
        List<String> commands = Arrays.asList(
                "/login",
                "/logout",
                "/message",
                "/command" +
                        "\n----------\n" +
                        "get messages\n" +
                        "get products\n" +
                        "purchase\n" +
                        "remove product(admin)\n" +
                        "add product(admin)\n" +
                        "----------"
        );
        HelpDto helpDto = new HelpDto();
        helpDto.setHelpList(commands);
        helpDto.setType(Type.ONE);
        helpDto.setName("help");
        return helpDto;
    }
}
