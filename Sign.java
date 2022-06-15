public enum Sign { // т.к. референсы "Bye-bye!" - "World!" или "Hi World!" - "World!", ищем знак между ДВУМЯ пробелами
    PLUS(" + ", '+'), MINUS(" - ", '-'),
    MUL(" * ", '*'), DIV(" / ", '/');

    public String signSpace;
    public char sign;

     Sign(String signSpace, char sign) {
        this.signSpace = signSpace;
        this.sign = sign;
    }

    public char getSign() {
         return sign;
    }
}
