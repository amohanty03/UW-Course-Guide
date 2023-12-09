package com.cs407.uwcourseguide;

public class SqlToJsonParser {
}


/*
import re
import json

def process_sql_file(file_path):
    # Read the SQL file
    with open(file_path, 'r') as file:
        sql_content = file.read()

    # Regular expression pattern for 'INSERT INTO' statements
    insert_pattern = re.compile(r"INSERT INTO (\w+) \((.*?)\) VALUES \((.*?)\);", re.DOTALL)

    # Extract 'INSERT INTO' statements
    insert_statements = insert_pattern.findall(sql_content)

    # Process each 'INSERT INTO' statement
    firebase_data = {}
    for table, columns, values in insert_statements:
        # Splitting columns and values, and cleaning them
        columns = columns.split(", ")
        values = re.split(r",(?=(?:[^']*'[^']*')*[^']*$)", values)  # Split by commas not inside quotes
        values = [v.strip().strip("'").replace("''", "'") for v in values]

        # Create a dictionary for the row
        row_data = dict(zip(columns, values))

        # Use a unique identifier for each row (assuming the first two columns form a unique key)
        row_id = f"{row_data[columns[0]]}-{row_data[columns[1]]}"

        # Add to firebase data
        if table not in firebase_data:
            firebase_data[table] = {}
        firebase_data[table][row_id] = row_data

    return firebase_data

# Replace 'your_sql_file.sql' with the path to your SQL file
file_path = 'your_sql_file.sql'
firebase_structure = process_sql_file(file_path)

# Optionally, save the Firebase structure to a JSON file
with open('firebase_data.json', 'w') as json_file:
    json.dump(firebase_structure, json_file, indent=4)

print("Conversion complete. Firebase structure saved to 'firebase_data.json'.")
 */