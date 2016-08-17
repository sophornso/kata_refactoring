Gilded Rose Refactoring
=========================

This is a solution kata implementation based on the kata refactoring subject from:
https://github.com/wouterla/GildedRose

A strategy pattern is implemented that will define how to calculate the quality depending of the product type.
This strategy is in fact used with ProductType enum where each specific product type can have its owner quality update strategy.
An abstract factory is used in order to be able to change easily the strategy implementations.

Notice that "Item" class is not modified as asked by the Kata subject, except its package location, and its indentation.

