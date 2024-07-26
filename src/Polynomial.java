import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Polynomial {
    List<Term> terms;

    public Polynomial(){
        terms = new LinkedList<>();
    }

    public static Polynomial readPoly(Scanner scan){
        String str = scan.nextLine();
        double coef;
        int exp;
        Polynomial poly = new Polynomial();
        Pattern p = Pattern.compile("([-+]\\d+)x?[\\^]?([-]?\\d+)?");
        Matcher m = p.matcher(str);
        while (m.find()){
            exp = 0;
            coef = Double.parseDouble(m.group(1));
            if(m.group(2) != null)
                exp = Integer.parseInt(m.group(2));
            poly.terms.add(new Term(coef, exp));
        }
        poly.sort(poly.terms.listIterator());
        return poly;
    }

    private void sort(ListIterator<Term> listIterator){
        if(!listIterator.hasNext()) {
            return;
        }
        Term term = listIterator.next(), term1;
        if(listIterator.hasNext()) {
            term1 = listIterator.next();
            if (term.compareTo(term1) < 0) {
                listIterator.remove();
                listIterator.previous();
                listIterator.add(term1);
                listIterator.previous();
            }
            if(listIterator.hasPrevious())
                listIterator.previous();
        }
        sort(listIterator);
    }

    public Polynomial addPoly(Polynomial poly){
        if(terms.isEmpty())
            return poly;
        if(poly.terms.isEmpty())
            return this;
        Polynomial result = new Polynomial();
        ListIterator<Term> poly1Iterator = terms.listIterator();
        ListIterator<Term> poly2Iterator = poly.terms.listIterator();
        Term term1, term2;
        while(poly1Iterator.hasNext() && poly2Iterator.hasNext()) {
            term1 = poly1Iterator.next();
            term2 = poly2Iterator.next();
            if (term1.compareTo(term2) > 0) {
                result.terms.add(term1);
                poly2Iterator.previous();
            } else if (term1.compareTo(term2) < 0) {
                result.terms.add(term2);
                poly1Iterator.previous();
            } else {
                result.terms.add(term1.addTerm(term2));
            }
        }
        while(poly1Iterator.hasNext()){
            result.terms.add(new Term(poly1Iterator.next()));
        }
        while(poly2Iterator.hasNext()){
            result.terms.add(new Term(poly2Iterator.next()));
        }
        return result;
    }

    public Polynomial multiplyPoly(Polynomial poly){
        Polynomial result = new Polynomial();
        ListIterator<Term> poly1Iterator = terms.listIterator();
        Term term , term1;
        while(poly1Iterator.hasNext()) {
            term = poly1Iterator.next();
            for(Term term2 : poly.terms){
                result.terms.add(term.multiplyTerm(term2));
            }
        }
        result.sort(result.terms.listIterator());
        ListIterator<Term> listIterator = result.terms.listIterator();
        while(listIterator.hasNext() ){
            term = listIterator.next();
            if(listIterator.hasNext()) {
                term1 = listIterator.next();
                if (term.compareTo(term1) == 0) {
                    listIterator.remove();
                    listIterator.previous();
                    listIterator.set(term.addTerm(term1));
                    listIterator.next();
                }
                listIterator.previous();
            }
        }
        return result;
    }

    @Override
    public String toString() {
        Iterator<Term> polyIterator = this.terms.listIterator();
        StringBuilder stringBuilder = new StringBuilder();
        Term term;
        int i = 0;
        while(polyIterator.hasNext()) {
            term = polyIterator.next();
            if(term.getCeof() > 0 && i == 0)
                stringBuilder.append(term.toString().substring(1) + " ");
            else
                stringBuilder.append(term + " ");
            i++;
        }
        return stringBuilder.toString();
    }

    private static class Term implements Comparable{
        private double ceof;
        private int exp;

        private Term(double ceof, int exp){
            setCeof(ceof);
            setExp(exp);
        }

        private Term(Term term){
            setCeof(term.getCeof());
            setExp(term.getExp());
        }

        public void setCeof(double ceof) {
            this.ceof = ceof;
        }

        public double getCeof() {
            return ceof;
        }

        public void setExp(int exp) {
            this.exp = exp;
        }

        public int getExp() {
            return exp;
        }

        private Term addTerm(Term term){
            return new Term(getCeof() + term.getCeof(), getExp());
        }

        private Term multiplyTerm(Term term){
            return new Term(getCeof() * term.getCeof(), getExp() + term.getExp());
        }

        @Override
        public String toString() {
            if(getCeof() == 0)
                return "\b";
            if(getExp() != 0) {
                if (getCeof() > 0)
                    return "+" + getCeof() + "x^" + getExp();
                return getCeof() + "x^" + getExp();
            }
            if (getCeof() > 0)
                return "+" + getCeof();
            return getCeof() + "";
        }

        @Override
        public int compareTo(Object o) {
            Term term = (Term) o;
            if(getExp() < term.getExp())
                return -1;
            else if(getExp() == term.getExp())
                return 0;
            else
                return 1;
        }
    }
}
