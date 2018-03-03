# Vault

Vault is desktop application which is simple representation of internet bank. Application was created only for learning purpose.

## Requirements

- Java SE Runtime Environment 8

## Features

- Panel for creating account

- After loggin in to the desktop window we see short informations about account
![](https://i.imgur.com/eMSxyRq.png)

- All payments history
![](https://i.imgur.com/cMhNwfy.png)

- Informations about bill
![](https://i.imgur.com/Z50L29E.png)

- Informations about cash flow from last three months
![](https://i.imgur.com/z1RkgCS.png)

- Panel to send transfers

## Contributing

Contributions to the Vault library are welcome. Please note the following guidelines before submiting your pull request.

- Write tests for new functions and added features
- Follow coding standards like in example below

 ### Code formatting example
 ```java
public class Foo {
    public int[] X = new int[]{1, 3, 5, 7, 9, 11};

    public void foo(boolean a, int x, int y, int z) {
        label1:
        do {
            try {
                if (x > 0) {
                    int someVariable = a ? x : y;
                    int anotherVariable = a ? x : y;
                } else if (x < 0) {
                    int someVariable = (y + z);
                    someVariable = x = x + y;
                } else {
                    label2:
                    for (int i = 0; i < 5; i++) doSomething(i);
                }
                switch (a) {
                    case 0:
                        doCase0();
                        break;
                    default:
                        doDefault();
                }
            } catch (Exception e) {
                processException(e.getMessage(), x + y, z, a);
            } finally {
                processFinally();
            }
        }
        while (true);

        if (2 < 3) return;
        if (3 < 4) return;
        do {
            x++;
        }
        while (x < 10000);
        while (x < 50000) x++;
        for (int i = 0; i < 5; i++) System.out.println(i);
    }

    private class InnerClass implements I1, I2 {
        public void bar() throws E1, E2 {
        }
    }
}
```

## License

Vault is licensed under the [MIT License](http://opensource.org/licenses/MIT).

##Author

Wojciech Kozioł