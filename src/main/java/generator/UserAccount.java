package generator;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Random;

/**
 * @author rassoll
 * @created 25.10.2017
 * @$Author$
 * @$Revision$
 */
public class UserAccount implements Serializable
{
    private String login;
    private String firstName;
    private String lastName;
    private String patronymic;
    private LocalDate dateOfBirth;

    private Random random = new Random();

    public UserAccount(String firstName, String lastName, String patronymic)
    {
        this.firstName = firstName;
        this.lastName = lastName;
        this.patronymic = patronymic;
        dateOfBirth = generateDateOfBirth();
        login = getLogin();
    }

    private LocalDate generateDateOfBirth()
    {
        return LocalDate.now()
                .minusYears(random.nextInt(50))
                .minusMonths(random.nextInt(50))
                .minusWeeks(random.nextInt(50))
                .minusDays(random.nextInt(50));
}

    private String getLogin()
    {
        return String.format("%S%S%s_%s_%s_%s",
                Transliterator.transliterate(firstName.substring(0, 1)),
                Transliterator.transliterate(patronymic.substring(0, 1)),
                Transliterator.transliterate(lastName),
                dateOfBirth.getDayOfMonth(),
                dateOfBirth.getMonthValue(),
                dateOfBirth.getYear()
        );
    }

    public String getFirstName()
    {
        return firstName;
    }

    public String getLastName()
    {
        return lastName;
    }

    public String getPatronymic()
    {
        return patronymic;
    }

    @Override
    public String toString()
    {
        return "UserAccount{" + "login='" + login + '\'' + ", firstName='" + firstName + '\'' + ", lastName='" + lastName + '\'' + ", patronymic='" + patronymic + '\'' + ", dateOfBirth=" + dateOfBirth + '}';
    }
}
