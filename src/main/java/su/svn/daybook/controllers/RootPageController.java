/*
 * This file was last modified at 2020.11.15 22:00 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * RootPageController.java
 * $Id$
 */

package su.svn.daybook.controllers;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RootPageController {
    private static final String response = "{\n" +
            "  \"headers\": {\n" +
            "    \"resultCount\": 1000\n" +
            "  },\n" +
            "  \"data\":[\n" +
            "    {\"vin\":\"ee8a89d8\",\"brand\":\"Fiat\",\"year\":1987,\"color\":\"Вчера во время проведения разведоперации наша группа подверглась нападению неизвестного противника в камуфляжной форме Алиенов. В результате эффективной обороны и стремительной контратаки многочисленная группа боевиков была смята и отброшена. Среди личного состава потерь нет. Бойцы разведгруппы проявили недюжие навыки владения оружием. Особо отличился в бою взводный Кудряшев&nbsp;М.А., грамотно использовавший человеческие ресурсы своего взвода. В результате операции были захвачены элементы внеземной культуры, которые переданы аналитической группе.\"},\n" +
            "    {\"vin\":\"642b3edc\",\"brand\":\"Renault\",\"year\":1968,\"color\":\"White\"},\n" +
            "    {\"vin\":\"19ec7580\",\"brand\":\"Renault\",\"year\":1981,\"color\":\"Black\"},\n" +
            "    {\"vin\":\"39980f30\",\"brand\":\"VW\",\"year\":1986,\"color\":\"Red\"},\n" +
            "    {\"vin\":\"ec9cc4e4\",\"brand\":\"Fiat\",\"year\":1981,\"color\":\"Brown\"},\n" +
            "\n" +
            "    {\"vin\":\"09a06548\",\"brand\":\"VW\",\"year\":1965,\"color\":\"Green\"},\n" +
            "    {\"vin\":\"05c47246\",\"brand\":\"Mercedes\",\"year\":2007,\"color\":\"Blue\"},\n" +
            "    {\"vin\":\"a9cb87aa\",\"brand\":\"Fiat\",\"year\":1962,\"color\":\"Green\"},\n" +
            "    {\"vin\":\"eae758fa\",\"brand\":\"BMW\",\"year\":1999,\"color\":\"Yellow\"},\n" +
            "    {\"vin\":\"1241c403\",\"brand\":\"Jaguar\",\"year\":1964,\"color\":\"Yellow\"},\n" +
            "\n" +
            "    {\"vin\":\"13f853a7\",\"brand\":\"Honda\",\"year\":2006,\"color\":\"White\"},\n" +
            "    {\"vin\":\"447d9ed9\",\"brand\":\"Jaguar\",\"year\":2005,\"color\":\"Orange\"},\n" +
            "    {\"vin\":\"78fa052e\",\"brand\":\"Jaguar\",\"year\":1990,\"color\":\"Orange\"},\n" +
            "    {\"vin\":\"8b77772a\",\"brand\":\"Mercedes\",\"year\":1991,\"color\":\"Blue\"},\n" +
            "    {\"vin\":\"23ba7e86\",\"brand\":\"Honda\",\"year\":1975,\"color\":\"Yellow\"},\n" +
            "\n" +
            "    {\"vin\":\"9bacb32d\",\"brand\":\"Volvo\",\"year\":1968,\"color\":\"Brown\"},\n" +
            "    {\"vin\":\"62094d91\",\"brand\":\"Mercedes\",\"year\":1962,\"color\":\"Green\"},\n" +
            "    {\"vin\":\"dc7003f4\",\"brand\":\"Jaguar\",\"year\":1976,\"color\":\"Maroon\"},\n" +
            "    {\"vin\":\"08607aef\",\"brand\":\"Mercedes\",\"year\":1987,\"color\":\"Maroon\"},\n" +
            "    {\"vin\":\"45eee33a\",\"brand\":\"BMW\",\"year\":1980,\"color\":\"Silver\"}\n" +
            "  ]\n" +
            "}\n";

    @Operation(hidden = true)
    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/api/v1/pages", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> readAbTest(@Param("page") Long page) {
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
