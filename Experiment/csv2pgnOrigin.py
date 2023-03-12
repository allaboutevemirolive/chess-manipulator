import sys

input_filename = sys.argv[1]
output_filename = sys.argv[2]

def main():
    # Check if the user provided the input and output filenames
    if len(sys.argv) != 3:
        print("Usage: python CSVToPGN.py <input.csv> <output.pgn>")
        sys.exit(1)

    # Open the input and output files
    with open(sys.argv[1]) as input_file, open(sys.argv[2], "w") as output_file:
        # Read each line of the input file and write it to the output file as a PGN game
        for line in input_file:
            fields = line.strip().split(",")
            if len(fields) < 11:
                continue
            puzzle_id = fields[0]
            fen = fields[1]
            moves = fields[2]
            rating = fields[3]
            rating_deviation = fields[4]
            popularity = fields[5]
            nb_plays = fields[6]
            themes = fields[7]
            game_url = fields[8]
            opening_family = fields[9]
            opening_variation = fields[10]

            output_file.write(f'[Event "{puzzle_id}"]\n')
            output_file.write(f'[FEN "{fen}"]\n')
            output_file.write(f'[Site "{game_url}"]\n')
            output_file.write(f'[Rating "{rating}"]\n')
            output_file.write(f'[RatingDeviation "{rating_deviation}"]\n')
            output_file.write(f'[Popularity "{popularity}"]\n')
            output_file.write(f'[NbPlays "{nb_plays}"]\n')
            output_file.write(f'[Themes "{themes}"]\n')
            output_file.write(f'[OpeningFamily "{opening_family}"]\n')
            output_file.write(f'[OpeningVariation "{opening_variation}"]\n')
            output_file.write(f'{moves} 1-0\n\n')

if __name__ == "__main__":
    main()
