# machineLearning

Coursework 2 of the AI module in Middlesex University.
The goal of the coursework is to categorise handwritten digits. Two datasets come from the University of California at Irvine's Machine Learning Repository (Optical Recognition of Handwritten Digits Data Set). 

The first step is to use euclidean distance to find the closest digit vector in the data set 1 for each digit vector in the data set 2. The efficiency of this method is calculated and displayed. 

The second step is to use a support vector machine with a reduction of the current data set to 2 dimensions. 



## Start in Eclipse

Open Run Configuration

Under the tab "Argument", add the following: 

- The path of the first data set. Example: 

  ```
  /Users/CC/Documents/CS/university/Year_03/AI/coursework2.2/cw2DataSet1
  ```

- The path of your second data set. Example: 

  ```
  /Users/CC/Documents/CS/university/Year_03/AI/coursework2.2/cw2DataSet2.csv
  ```

- What mode you want to run. The possible mode are 'euclidean', 'svm', 'debug'. 

At the end it should look like this: 

```
/Users/CC/Documents/CS/university/Year_03/AI/coursework2.2/cw2DataSet1.csv /Users/CC/Documents/CS/university/Year_03/AI/coursework2.2/cw2DataSet2.csv svm
```

