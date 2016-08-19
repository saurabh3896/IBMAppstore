from __future__ import print_function
import numpy as np

def sort_rowm(matrix, row, n) :
    #a.view('i8', 'i8', 'i8') returns a numpy array along with the dtype keyword, where ['fi'] refers to ith column, thus
    #providing appropriate candidate for 'order' keyword in np.sort() method
    arg = 'i8,'*n
    arg = arg[:len(arg) - 1]
    return np.sort(matrix.view(arg), order = ['f' + str(row)], axis = 0)

def create(dimension) :
    matrix = np.zeros((dimension, dimension), dtype = int)
    i, j = np.indices(matrix.shape)
    matrix[i,j] = i + j
    return matrix

print("Enter n for NxN array : ", end = '')
n = input()

rows1 = [(n/2)*[0, 1] + (n % 2)*[0]]
rows2 = [(n/2)*[1, 0] + (n % 2)*[1]]
output_matrix = np.row_stack((n/2)*(rows1, rows2) + (n % 2)*(rows1, ))
print(output_matrix)

print(sort_rowm(np.array([[1, 2, 3, 4], [4, 1, 5, 6], [0, 4, 2, 1], [9, -1, 8, 4]]), 2, 4))

print(create(10))
