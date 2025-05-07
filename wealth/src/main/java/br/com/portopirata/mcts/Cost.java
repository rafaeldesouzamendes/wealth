package br.com.portopirata.mcts;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class Cost<T> {
    private T unit;
    private BigDecimal cost;
}
