package fortask;

/**
 * Created by Роман Лотоцький on 13.05.2017.
 */
enum Demo {
    DEMO;

    int i = 10;

    {
        i--;
    }

    {
        --i;
    }

    private Demo() {
        i = i-- + --i;
    }
}

