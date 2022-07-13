public class Main {

    public static int bossHealth = 2500;
    public static int bossDamage = 40;
    public static String bossDefendsType = "";
    public static int[] heroesHealth = {400, 310, 390, 800, 350, 250, 330, 200};
    public static int[] heroesDamage = {20, 25, 15, 2, 5, 10, 30, 0};
    public static String[] heroesAttackType = {"Physical", "Magical", "Kinetic", "Golem", "Lucky", "Berserk", "Thor", "Medic"};
    public static int roundNumber = 1;
    public static int healHeroes = 70;
    public static boolean luckPlayer, stunThor, golemBuff, randomMiss;;
    public static int minValue = heroesHealth[0];

    public static void main(String[] args) {
        printStatistics();
        while (!isGameFinished()) {
            chooseDefence();
            System.out.println("ROUND " + (roundNumber++));
            round();
        }
    }

    public static void setHealHeroes() {
        if (heroesHealth[7] > 0) {
            for (int i = 0; i < heroesHealth.length; i++) {
                if (heroesHealth[i] <= 0) {
                    heroesHealth[i] = 0;
                }
                else if (heroesHealth[i] < 100 && heroesHealth[i] > 1) {
                    System.out.println(heroesHealth[i]);
                    minValue = heroesHealth[i];
                    heroesHealth[i] = minValue + healHeroes;
                    System.out.println(heroesHealth[i]);
                    break;
                }
            }
        }
    }



    public static void printStatistics() {
        System.out.println(" ");
        System.out.println("Boss health: " + bossHealth + " [" + bossDamage + "]");
        for (int i = 0; i < heroesHealth.length; i++) {
            System.out.println(heroesAttackType[i] + " health: " + heroesHealth[i] + " [" + heroesDamage[i] + "]");
        }
        System.out.println("_______________");
        System.out.println(" ");
    }

    public static void chooseDefence() {
        Random random = new Random();
        int randomIndex = random.nextInt(heroesAttackType.length);
        bossDefendsType = heroesAttackType[randomIndex];
        System.out.println("Boss chose: " + bossDefendsType);

    }

    public static boolean isGameFinished() {
        if (bossHealth <= 0) {
            System.out.println("Heroes win!!!");
            return true;
        }
        boolean allHeroesDead = true;
        for (int i = 0; i < heroesHealth.length; i++) {
            if (heroesHealth[i] > 0) {
                allHeroesDead = false;
                break;
            }
        }
        if (allHeroesDead) System.out.println("Boss won!!!");
        return allHeroesDead;
    }

    public static void bossHits() {
        if (stunThor) System.out.println("Босс получил оглушение от Тора и не может ударить. ");
        else {
            if (heroesHealth[3] > 0) {
                heroesHealth[3] = heroesHealth[3] + bossDamage;
                heroesHealth[3] = heroesHealth[3] - (bossDamage + (bossDamage / 5) * heroesHealth.length)-(bossDamage/5);

                golemBuff = true;
                if (heroesHealth[3] - bossDamage < 0) {
                    heroesHealth[3] = 0;  //
                }
            }
            else golemBuff = false;
            if (heroesHealth.length > 0 && bossHealth > 0) {
                Random random = new Random();
                randomMiss = random.nextBoolean();
                if (randomMiss && heroesHealth[4] > 0) {
                    heroesHealth[4] += bossDamage;
                    System.out.println("Лаки не получил урона. ");
                    luckPlayer = false;
                }
                if (!randomMiss && heroesHealth[5] > 0) {
                    heroesHealth[5] += bossDamage / 2;
                    System.out.println("Берсерк был усилен. ");
                    luckPlayer = true;
                }
            }
            for (int i = 0; i < heroesHealth.length; i++) {
                if (heroesHealth[i] > 0 && bossHealth > 0) {
                    if (heroesHealth[i] - bossDamage < 0) {
                        heroesHealth[i] = 0;  //
                    } else if (golemBuff) {
                        heroesHealth[i] = heroesHealth[i] - bossDamage;
                        heroesHealth[i] = heroesHealth[i] + (bossDamage / 5);
                    } else heroesHealth[i] = heroesHealth[i] - bossDamage;
                }
            }
        }
        stunThor = false;
    }

    public static void heroesHits() {
        if (heroesHealth[6] > 0) {
            Random randomThor = new Random();
            boolean randomMiss = randomThor.nextBoolean();
            if (randomMiss) {
                Random random1 = new Random();
                boolean randomMiss1 = random1.nextBoolean();
                if (randomMiss1) {
                    stunThor = true;
                }
            }
        }
        for (int i = 0; i < heroesDamage.length; i++) {
            if (bossHealth > 0 && heroesHealth[i] > 0) {
                if (bossHealth - heroesDamage[i] < 0) {
                    if (luckPlayer && heroesHealth[5] > 0) {
                        bossHealth = bossHealth - heroesDamage[5] * (bossDamage / 2);
                        return;
                    }
                    bossHealth = 0;
                } else bossHealth = bossHealth - heroesDamage[i];
/Обычный урон героев по боссу

                if (bossDefendsType == heroesAttackType[i]) {
                    Random random = new Random();
                    int kef = random.nextInt(9) + 5;
                    if (bossHealth - heroesDamage[i] * kef < 0) {
                        bossHealth = 0;
                        System.out.println("Crit = " + kef * heroesDamage[i]);
                    } else
                        bossHealth = bossHealth - heroesDamage[i] * kef;
                    System.out.println("Crit = " + kef * heroesDamage[i]);
                }

            }
        }
    }

    public static void round() {
        setHealHeroes();
        bossHits();
        heroesHits();
        printStatistics();
    }
}



