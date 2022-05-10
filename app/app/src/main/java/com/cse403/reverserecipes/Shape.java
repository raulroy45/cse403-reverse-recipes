package self.is.ccahill.com.au.shapelist;

import java.util.Comparator;

public class Shape
{
    private String id;
    private String name;
    private int image;
    private String type;

    public Shape(String id, String name, String type) {
        this.id = id;
        this.name = name;
        this.type = type;
//        this.image = image;
    }

    public String getType() {return this.type;}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public static Comparator<Shape> idAscending = new Comparator<Shape>()
    {
        @Override
        public int compare(Shape shape1, Shape shape2)
        {
            int id1 = Integer.valueOf(shape1.getId());
            int id2 = Integer.valueOf(shape2.getId());

            return Integer.compare(id1, id2);
        }
    };

    public static Comparator<Shape> nameAscending = new Comparator<Shape>()
    {
        @Override
        public int compare(Shape shape1, Shape shape2)
        {
            String name1 = shape1.getName();
            String name2 = shape2.getName();
            name1 = name1.toLowerCase();
            name2 = name2.toLowerCase();

            return name1.compareTo(name2);
        }
    };


}
