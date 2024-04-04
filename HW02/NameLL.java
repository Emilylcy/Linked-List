public class NameLL {
    Node head;
    Node tail;
    private int size;

    public NameLL() {
        head = new Node(null);
        tail = new Node(null);
        head.next = tail;
        tail.prev = head;
        size = 0;
    }

    public class Node {
        public Name name;
        public Node next;
        public Node prev;

        public Node(Name name) {
            this.name = name;
            this.next = null;
            this.prev = null;
        }
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public void insertFirst(Name name) {
        Node newNode = new Node(name);
        newNode.next = head.next;
        head.next.prev = newNode;
        head.next = newNode;
        newNode.prev = head;
    }

    public void insertBack(Name name){
        Node newNode = new Node(name);
        newNode.prev = tail.prev;
        newNode.next = tail;
        tail.prev.next = newNode;
        tail.prev = newNode;    
    }

    public void insertBefore(Name name, Node n){
        Node nodeToInsert = new Node(name);

        nodeToInsert.prev = n.prev;
        nodeToInsert.next = n;
        n.prev.next = nodeToInsert;
        n.prev = nodeToInsert;
    }

    public void insertSortedAlpha(Name name){
        Node current = head.next;
        while (current != tail){
            if (name.getName().compareTo(current.name.getName())<0){
                insertBefore(name,current);     
                return;
            } 
            current = current.next;
            }
        Node newNode = new Node(name);
        newNode.next = tail;
        newNode.prev = tail.prev;
        tail.prev.next = newNode;    
        tail.prev = newNode;     
    }    

    public void insertSortedRank(Name name){
        Node current = head.next;
        while(current != tail){
            if(current.name.getNumber() <= name.getNumber()){
                insertBefore(name,current);
                return;
            }
            current = current.next;
        }
        Node newNode = new Node(name);
        newNode.next = tail;
        newNode.prev = tail.prev;
        tail.prev.next = newNode;    
        tail.prev = newNode; 
    }

    public void remove(Name name) {
        Node current = head.next;
        Node leftNode = head;

        while (current != tail) {
          if (current.name.getName().equals(name.getName())) {
            leftNode.next = current.next;
            current.next.prev = leftNode;
            size--;
            return;
          }
          leftNode = current;
          current = current.next;
        }
    
      }
      public int getNumber(int index){
        Node current = head.next;
        int currentIndex = 1;
        while(current != tail){
            if(currentIndex == index){
                return current.name.getNumber();
            }
            current = current.next;
            currentIndex++;
        }
        
        return 0;
    }

    public void set(int index,Name newName){
        Node current = head.next;
        int currentIndex = 1;
        while (current != tail) {
            if (currentIndex == index) {
                current.name = newName;
            }
            current = current.next;
            currentIndex++;
        }  
    }

    public int index(String name) {
        Node current = head.next;
        int index = 1; 
        while (current != tail) {
            if (current.name.getName().compareTo(name) == 0) {
                return index;
            }
            current = current.next;
            index++;
        }
        return -1; 
    }


    public double[] yearStats(String name, int year, char gender) {
        double[] yearStats = new double[3];
        Node current = head.next;
        while (current != tail) {
            if (current.name.getName().equals(name) && current.name.getYear() == year && current.name.getGender() == gender) {
                yearStats[0] = current.name.getRank();
                yearStats[1] = current.name.getNumber();
                yearStats[2] = current.name.getPercentage();
                return yearStats;
            }
            current = current.next;
        }
        return null;
    }

    

    public double[] totalStats(String name, char gender) {
        double[] totalStats = new double[3];
        Node current = head.next;
        int index = 1;
    
        while (current != tail) {
            if (current.name.getName().equals(name) && current.name.getGender() == gender) {
                totalStats[0] = index;
                totalStats[1] = current.name.getNumber();
                totalStats[2] = current.name.getPercentage();
                return totalStats;
            }
            current = current.next;
            index++;
        }

        return null;
    }
    

    public String toString(){
        String result = "";
        Node current = head.next;
        while (current != tail){
            result += current.name.getName();
            if (current.next != tail){
                result += ", ";
            }
            current = current.next;
        }
        return result;
    }
}