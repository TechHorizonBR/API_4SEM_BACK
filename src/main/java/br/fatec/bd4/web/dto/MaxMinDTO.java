package br.fatec.bd4.web.dto;

import com.fasterxml.jackson.annotation.JsonAlias;

public record MaxMinDTO(
        @JsonAlias("maxLatitude") Double maxLatitude,
        @JsonAlias("maxLongitude") Double maxLongitude,
        @JsonAlias("minLatitude") Double minLatitude,
        @JsonAlias("minLongitude") Double minLongitude
) {
}
