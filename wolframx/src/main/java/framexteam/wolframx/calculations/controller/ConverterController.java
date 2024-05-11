package framexteam.wolframx.calculations.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import lombok.*;

import framexteam.wolframx.calculations.operations.converter.NumberConverter;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/calculations/converter")
@Tag(name = "Вычисления", description = "API для выполнения математических операций")
public class ConverterController {

    private static final Logger logger = LogManager.getLogger(ConverterController.class);

    @PostMapping
    @Operation(summary = "Перевод числа из одной системы счисления в другую",
               description = "Переводит число из одной системы счисления в другую.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Результат перевода числа", 
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = NumberConverterResponse.class))),
        @ApiResponse(responseCode = "400", description = "Ошибка в формате входных данных")
    })
    public ResponseEntity<?> convertNumber(@RequestBody NumberConverterRequest request) {
        try {
            logger.info("Received request to convert number: {}", request);

            String number = request.getNumber();
            int sourceBase = request.getSourceBase();
            int targetBase = request.getTargetBase();

            String convertedNumber = NumberConverter.convert(number, sourceBase, targetBase);

            NumberConverterResponse response = new NumberConverterResponse();
            response.setConvertedNumber(convertedNumber);

            logger.info("Number conversion completed successfully.");
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            logger.error("Error during number conversion: {}", e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @Getter
    @Setter
    private static class NumberConverterRequest {
        private String number;
        private int sourceBase;
        private int targetBase;
    }

    @Getter
    @Setter
    private static class NumberConverterResponse {
        private String convertedNumber;
    }
}