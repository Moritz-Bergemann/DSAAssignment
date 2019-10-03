/* Sortable by Moritz Bergemann
 * Interface for all classes that are to be sorted by their class fields
 *  (even if these class fields should not be accessible in any other way
 */
public interface Sortable
{
    String getSortValue();
}
