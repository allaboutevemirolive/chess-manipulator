import csv

# specify the input and output file paths
input_file = "input.csv"
output_file = "output.csv"

# specify the rating threshold
rating_threshold = 2500

# open the input and output files
with open(input_file, "r") as in_file, open(output_file, "w") as out_file:
    # create a CSV reader and writer
    reader = csv.reader(in_file)
    writer = csv.writer(out_file)

    # write the header row to the output file
    writer.writerow(next(reader))

    # iterate over the rows in the input file
    for row in reader:
        # check if the rating is greater than the threshold
        if int(row[3]) > rating_threshold:
            # write the row to the output file
            writer.writerow(row)
