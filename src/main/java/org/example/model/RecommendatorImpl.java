package org.example.model;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.example.annotation.InjectProperty;
import org.example.interfaces.Recommendator;

@AllArgsConstructor
@NoArgsConstructor
public class RecommendatorImpl implements Recommendator {

    @InjectProperty("alcohol")
    private String drinkName;
    @Override
    public void recommend() {
        System.out.println(" drink " + drinkName);
    }
}
