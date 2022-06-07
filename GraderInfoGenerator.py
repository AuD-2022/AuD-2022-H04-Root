import os
import json
import re
from collections import defaultdict
from pathlib import Path

exclude = ['grader-info.json']

source_sets = [
    {'dir': 'src/main/java', 'set': 'solution'},
    {'dir': 'src/test/java', 'set': 'solution'},
    {'dir': 'src/grader/java', 'set': 'grader'},
    {'dir': 'src/main/resources', 'set': 'solution'},
    {'dir': 'src/test/resources', 'set': 'solution'},
    {'dir': 'src/grader/resources', 'set': 'grader'}
]

def main():
    info_json = None
    sets = defaultdict(lambda: [])
    for source_set in source_sets:
        d = Path(source_set['dir'])
        for root, dirs, files in os.walk(d):
            root = Path(root)
            for file in files:
                if file in exclude:
                    info_json = d.joinpath(file)
                file = root.joinpath(file)
                sets[source_set['set']].append(str(file.relative_to(d)))
    name = re.search(r'rootProject.name = "(?P<name>.+?)"', open('settings.gradle.kts', 'r').read())['name']
    assignment_id = re.search(r'assignmentId\s+=\s+"(?P<id>.+?)"', open('build.gradle.kts', 'r').read())['id']
    r = {
        "name": name,
        "assignmentIds": [assignment_id],
        "sourceSets":
            [{
                "name": k,
                "files": v
            } for k, v in sets.items()]
    }
    if info_json is not None:
        open(info_json, 'w').write(json.dumps(r))
        print(f'Successfully wrote information into file {info_json}.')
    else:
        print(f'Failed to write information into file: file not found.')

if __name__ == '__main__':
    main()
