public class ExpandableArray <E> {
    private E[] array; // Array to store items
    private int size;        // Number of items currently stored

    // Value constructor
    public ExpandableArray(int initialCapacity) {
        this.array = (E[])new Object[initialCapacity];
        this.size = 0;
    }

    public ExpandableArray(){
        this(10);
    }

    public void insert(E element){
        if (size == array.length){//our internal array is full
            E[]array2 = (E[])new Object[size*2];
            for (int i =0; i< size; i++){ 
                array2[i+1]=array[i];
            }
            array2[0] = element;
            this.array = array2;
        } else{
            for (int i = size; i > 0; i--){
                array[i]=array[i-1];
            }
            array[0] = element;
        }      
        this.size++;
    }

    public void insert(E element, int index){
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Invalid index");
        }
        if (size == array.length){
            int newCapacity = array.length*2;
            E[]array2 = (E[])new Object[newCapacity];
            for (int i =0; i< index; i++){ 
                array2[i]=array[i];
		    }
            array2[index] = element;
            for (int i = index; i <size; i++){
                array2[i+1]=array[i];
            }
            this.array = array2;
         } else {
            for (int i = size-1; i >= index; i--){
                array[i + 1] = array [i];
            }
            array[index] = element;
         }
         size++;
    }

    public E get(int index){
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Invalid index");
        }
        return array[index];
    }

    public E remove(int index){
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Invalid index");
        }
        E removed = array[index];
        for(int i = index; i< size - 1; i++){
                array[i] = array[i+1];
        }
        array[size-1] = null;
        size--;  
        return removed;
    }
    
    public String toString() {
        String result = "";
        for (int i = 0; i < size; i++) {
            result += array[i];
            if (i < size - 1) {
                result += ", ";
            }
        }
        return result;
    }
    
   public void set(E element, int index) {
    if (index < 0 || index >= size) {
        throw new IndexOutOfBoundsException("Invalid index");
    }
    array[index] = element;
   }

   public int size(){
     return size;
   }

}
