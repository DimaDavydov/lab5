package ru.dima.console;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CommandError {
  INVALID_SHORTIES("Неверно введен экземпляр Shorties");

  private String errorText;
}
