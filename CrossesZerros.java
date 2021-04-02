import java.util.Random;
import java.util.Scanner;

public class CrossesZerros {

    public static char[][] map;  // объявили карту
    public static final int Size = 3; // объявили размер
    public static final int Dots_To_Wins = 3; // условия победы
    public static final char Dot_Empty = '*'; // пустая точка
    public static final char Dot_X = 'x'; // инициализация крестика
    public static final char Dot_0 = '0'; // иниц.нолика
    public static final Scanner scanner = new Scanner(System.in);
    public static final Random random= new Random(Size);
    public static int Current_Dots=0;  // здесь будем считать число одинаковых знаков на линии
    public static int Coord1, Coord2; // координаты точки, в которые компьютер будет ставить ноль, сделаны публичными,
                                      // чтобы можно было менять их из разных методов



    public static void initMap() {  // создание карты
        map = new char[Size][Size];
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                map[i][j] = Dot_Empty;

            }
        }

    }

    public static void printMap() {

        for (int i = 0; i <= map.length; i++) {
            System.out.print(i + " ");
        }
        System.out.println();

        for (int i = 0; i < map.length; i++) {
            System.out.print((i + 1) + " ");
            for (int j = 0; j < map[i].length; j++) {
                System.out.print(map[i][j] + " ");

            }
            System.out.println();
        }
    }

    public static void humanTurn() {
        int x;
        int y;
        do {
            x=0;  // защита от Шамы
            y=0;  //  >:[
            System.out.println("Введите координаты");
            x = scanner.nextInt() - 1;
            y = scanner.nextInt() - 1;

        } while (!iscellValid(x, y));
        map[x][y] = Dot_X;


    }

    public static boolean iscellValid(int x, int y) {
        if (x < 0 || y < 0 || x >= Size || y >= Size) {
            return false;
        }
        if (map[x][y] == Dot_Empty) {

            return true;
        }

        return false;
    }


    /**
     * Логика описывает поиск в таблице мест, где на одной линии(вертикальной, гаризонтальной или диагональной)
     * Находятся два одинаковых знака и проверяет есть ли на линии свободное место для хода.
     * @param dot (Dot_X || Dot_0)
     *        Затем меняет переменные Coord1 и Coord2 на
     */
    public static void solution (char dot){ // принимаем крестик или нолик искать


/**
 * проверка на пример нескольких одинаковых знаков(не пустых) в ряд
 */
    for(int i=0;i<map.length;i++)
    {

        for(int j=0;j<map[i].length; j++){

            if(  map[i][j]==dot)   // если точка равно искомой
            {
                Current_Dots = Current_Dots+1; // увеличиваем счетчик знаков подряд на 1
                if (Current_Dots==Dots_To_Wins-1) // Если счетчик на 1 меньше, чем условие победы,
                {                                   // значит можно выиграть или остановить выигрыш игрока
                    for(int k=0;k<map[i].length; k++){   // пройдем строку заново
                        if (map[i][k]==Dot_Empty){  // ищем пустую ячейку в строке
                            Coord1=i;   // присваиваем координаты для точки
                            Coord2=k;
                            Current_Dots = 0;
                            break;  // ломаем внутрениий цикл при нахождении
                        }
                    }
                }
            }


        }

        Current_Dots = 0; // обнулять счетчик в конце строки
    }


    /**
     * проверка на пример нескольких знаков в столбце
     */
    for(int j=0;j<map.length;j++)
    {

        for(int i=0;i<map[j].length; i++){
            if(  map[i][j]==dot)
            {
                Current_Dots = Current_Dots+1;
                if (Current_Dots==Dots_To_Wins-1)
                {
                    for(int k=0;k<map[j].length; k++){
                        if (map[k][j]==Dot_Empty){
                            Coord1=k;
                            Coord2=j;
                            Current_Dots = 0;
                            break;
                        }
                    }
                }
            }


        }

        Current_Dots = 0; // обнулять счетчик в конце строки
    }

    /**
     * проверка основной диагонали
     */

    for (int i =0, j = 0 ; i< map.length && j < map[i].length; i++ , j++) {
        if (map[i][j] == dot) {
            Current_Dots = Current_Dots + 1;
            if (Current_Dots == Dots_To_Wins-1) {
                for (int k =0, l = 0 ; k< map.length && l< map[i].length; k++ , l++) {
                    if (map[k][l]==Dot_Empty){
                        Coord1=k;
                        Coord2=l;
                        Current_Dots = 0;
                        break;
                    }
                }
            }
        }


    }
    Current_Dots = 0; // обнулять счетчик в конце диагонали

    /**
     * проверка побочной диагонали
     */
    for (int i =Size - 1, j = 0 ; i>= 0 && j < map[i].length; i-- , j++) {
        if (map[i][j] == dot) {
            Current_Dots = Current_Dots + 1;
            if (Current_Dots == Dots_To_Wins-1) {
                for (int k =Size - 1, l = 0 ; k>= 0 && l < map[i].length; k-- , l++) {
                    if (map[k][l]==Dot_Empty){
                        Coord1=k;
                        Coord2=l;
                        Current_Dots = 0;
                        break;

                    }

                }
            }
        }


    }
    Current_Dots = 0; // обнулять счетчик в конце хода
}

    public static void aiTurn(){

        do {
            Coord1 = random.nextInt(Size);
            Coord2 = random.nextInt(Size);
        }while(!iscellValid(Coord1,Coord2)); // генерируем случайные координаты


        solution(Dot_X);  // помешать нам - лучше, чем случайные => переписываем их, если такой вариант есть

        solution(Dot_0); // выиграть - еще лучше, чем помешать => переписываем, если есть возможность



        System.out.printf("Компьютер ходит : %d %d", Coord1+1,Coord2+1);
        System.out.println();
        map[Coord1][Coord2]=Dot_0;  // Делаем ход.

    }

    public static boolean chekWin(char dot){
        /**
         * проверка строк
         */
        for(int i=0;i<map.length;i++)
        {

            for(int j=0;j<map[i].length; j++){
                if(  map[i][j]==dot)
                {
                    Current_Dots = Current_Dots+1;
                    if (Current_Dots==Dots_To_Wins)
                    {
                        return true;
                    }
                }
                else{
                    Current_Dots = 0; // сброс счетчика при несовпадении
                }

            }

            Current_Dots = 0; // обнулять счетчик в конце строки
        }
/**
 *  проверка столбцов
 */

        for(int j=0;j<map.length;j++)
        {

            for(int i=0;i<map[j].length; i++){
                if(  map[i][j]==dot)
                {
                    Current_Dots = Current_Dots+1;
                    if (Current_Dots==Dots_To_Wins)
                    {
                        return true;
                    }
                }
                else{
                    Current_Dots = 0; // сброс счетчика при несовпадении
                }

            }

            Current_Dots = 0; // обнулять счетчик в конце столбца
        }

/**
 * проверка диагоналей
 */
    for (int i =0, j = 0 ; i< map.length && j < map[i].length; i++ , j++) {
        if (map[i][j] == dot) {
            Current_Dots = Current_Dots + 1;
            if (Current_Dots == Dots_To_Wins) {
                return true;
            }
        } else {
            Current_Dots = 0; // сброс счетчика при несовпадении
        }


    }
        Current_Dots = 0; // обнулять счетчик в конце диагонали


        for (int i =Size - 1, j = 0 ; i>= 0 && j < map[i].length; i-- , j++) {
            if (map[i][j] == dot) {
                Current_Dots = Current_Dots + 1;
                if (Current_Dots == Dots_To_Wins) {
                    return true;
                }
            } else {
                Current_Dots = 0; // сброс счетчика при несовпадении
            }


        }
        Current_Dots = 0; // обнулять счетчик в конце диагонали





        return false;



    }
    public static boolean isFull(){
     for (int i = 0; i < map.length; i++){
         for (int j = 0; j <map[i].length; j++){
             if (map[i][j]== Dot_Empty);
             return false;

         }

     }
return true;

    }


    public static void play() {
        while (true) {


            humanTurn();
            printMap();
            if (chekWin(Dot_X)) {
                System.out.println("Человек выиграл");

                break;
            }
            if (isFull()) {
                System.out.println("Ничья");
                break;
            }


            aiTurn();
            printMap();
            if (chekWin(Dot_0)) {
                System.out.println("компуктер выиграл");
                break;

            }

            if (isFull()) {
                System.out.println("Ничья");
                break;
            }
        }
    }

    public static void main(String[] args) {
        initMap();
        printMap();
        play();


    }




}
