package frc.team2412.robot.util;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

@FunctionalInterface
public interface Configurable<T> {

    T get();

    default Configurable<T> apply(Consumer<T> config){
       config.accept(get());
        return this;
    }

}
