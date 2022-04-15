package brickset;

import repository.Repository;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Represents a repository of {@code LegoSet} objects.
 */
public class LegoSetRepository extends Repository<LegoSet> {

    public LegoSetRepository() {
        super(LegoSet.class, "brickset.json");
    }

    /**
     * Returns the number of LEGO sets with the tag specified.
     *
     * @param tag a LEGO set tag
     * @return the number of LEGO sets with the tag specified
     */
    public long countLegoSetsWithTag(String tag) {
        return getAll().stream()
                .filter(legoSet -> legoSet.getTags() != null && legoSet.getTags().contains(tag))
                .count();
    }

    /**
     * Returns with a boolean value of the given name if at least one exist.
     *
     * @param name
     * @return with a boolean value of the given name if at least one exist
     */
    public boolean nameExistInGivenValue(String name) {
        return getAll().stream()
                .anyMatch(legoSet -> Objects.equals(legoSet.getName(), name));
    }

    /**
     * Prints the legoSet tags in reverse order only once each.
     */
    public void printTagsInReverseOrder() {
        getAll().stream()
                .filter(legoSet -> legoSet.getTags() != null)
                .flatMap(legoSet -> legoSet.getTags().stream())
                .distinct()
                .sorted(Comparator.reverseOrder())
                .forEach(System.out::println);
    }

    /**
     * Returns the sum of the legoSet pieces.
     *
     * @return the sum of the legoSet pieces
     */
    public String sumOfTheLegosetPieces() {
        return getAll().stream()
                .map(LegoSet::getPieces)
                .reduce(0, Integer::sum)
                .toString();
    }

    /**
     * Returns a list with boolean value of a given value.
     * @param value
     * @return a list with boolean value of a given value
     */
    public Map<Boolean, List<Integer>> piecesBiggerThanGivenValue(int value) {
        return getAll().stream()
                .map(LegoSet::getPieces)
                .sorted()
                .distinct()
                .collect(Collectors.partitioningBy(number -> number > value));
    }

    /**
     * Returns a collection of the packaging type.
     *
     * @return a collection of the packaging type
     */
    public Map<PackagingType, Long> collectPackagingType(){
        return getAll().stream()
                .map(LegoSet::getPackagingType)
                .filter(Objects::nonNull)
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
    }

    public static void main(String[] args) {
        var repository = new LegoSetRepository();
        System.out.println(repository.countLegoSetsWithTag("Microscale"));
        System.out.println(repository.nameExistInGivenValue("Heart"));
        repository.printTagsInReverseOrder();
        System.out.println(repository.sumOfTheLegosetPieces());
        System.out.println(repository.piecesBiggerThanGivenValue(250));
        System.out.println(repository.collectPackagingType());
    }

}