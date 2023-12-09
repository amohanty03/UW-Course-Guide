import re
import json
import time

def process_sql_file(file_path):
    # Read the SQL file
    with open(file_path, 'r') as file:
        sql_content = file.read()

    # Regular expression pattern for 'INSERT INTO' statements
    insert_pattern = re.compile(r"INSERT INTO (\w+)\s*\(([^)]+)\)\s*VALUES\s*\(([^)]+)\);", re.MULTILINE | re.DOTALL)

    # Extract 'INSERT INTO' statements
    insert_statements = insert_pattern.findall(sql_content)

    num_of_courses_extracted = 0
    num_of_courses_correctly_extracted = 0

    # Process each 'INSERT INTO' statement
    firebase_data = {}
    special_characters = set("$#[]./")
    for table, columns, values in insert_statements:
        num_of_courses_extracted += 1

        # Splitting columns and values, and cleaning them
        columns = columns.split(", ")
        values = re.split(r",(?=(?:[^']*'[^']*')*[^']*$)", values)  # Split by commas not inside quotes
        values = [v.strip().strip("'").replace("''", "'") for v in values]

        # Create a dictionary for the row
        row_data = dict(zip(columns, values))

        # Remove keys with empty keys or containing $#[]./
        row_data = {key: value for key, value in row_data.items() if
                    key and all(char not in key for char in "$#[]./")}

        if columns[1] not in row_data:
            continue

        # Use a unique identifier for each row (assuming the first two columns form a unique key)
        row_id = f"{row_data[columns[0]]}-{row_data[columns[1]]}"

        if not row_id:
            continue
        if any(char in special_characters for char in row_id):
            continue

        # Add to firebase data
        if table not in firebase_data:
            firebase_data[table] = {}

        if 'number' not in row_data:
            continue

        course_number = row_data['number']

        if not course_number:
            continue
        if any(char in special_characters for char in course_number):
            continue

        if row_id not in firebase_data[table]:
            firebase_data[table][row_id] = {}
        firebase_data[table][row_id][course_number] = row_data

        num_of_courses_correctly_extracted += 1

    print(f"\nThere are 14,778 courses. Extracted {num_of_courses_extracted} courses. Correctly extracted {num_of_courses_correctly_extracted} courses.\n")

    return firebase_data

# Replace 'your_sql_file.sql' with the path to your SQL file
file_path = 'db_init.sql'
firebase_structure = process_sql_file(file_path)

# Optionally, save the Firebase structure to a JSON file
with open('firebase_data.json', 'w') as json_file:
    json.dump(firebase_structure, json_file, indent=4)

print("Conversion complete. Firebase structure saved to 'firebase_data.json'.\n")
