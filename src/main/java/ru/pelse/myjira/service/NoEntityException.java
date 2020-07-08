package ru.pelse.myjira.service;

public class NoEntityException extends Exception {
    public NoEntityException(String message) {
        super(message);
    }

    @Override
    public String getMessage() {
        return super.getMessage() + " Не найдены запрашиваемые данные.";
    }
}
