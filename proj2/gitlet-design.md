# Gitlet Design Document

**Name**: Vasilii Perfilev

## Classes and Data Structures

### Main

This is the entry point to our program.
It takes in arguments from the command
line and based on the command (the first
element of the args array) calls the
corresponding command in Repository
which will actually execute the logic of
the command. It also validates the arguments
based on the command to ensure that enough
arguments were passed in.

#### Fields
This class has no fields and hence
no associated state:
it simply validates arguments
and defers the execution to the Repository class.


### Repository

This is where the main logic of our program
will live. This file will handle all of the
actual capers commands by reading/writing
from/to the correct file, setting up persistence,
and additional error checking.

It will also be responsible for
setting up all persistence within Gitlet.
This includes creating the **.gitler folder**
as well as internal folders for **commits**, **staging
area**, **objects (blobs and commits)**, **HEAD and
branches' heads pointer**
This class defers all Commit, Staging Area, Blob specific logic to the Dog class: for example, instead of having the CapersRepository class handle Dog serialization and deserialization, we have the Dog class contain the logic for that.

#### Fields

1. static final File CWD = new File(System.getProperty("user.dir"))
2. static final File GITLET_DIR = Utils.join(CWD, ".gitlet")
3. static final File HEAD_POINTER = Utils.join(GITLET_DIR, "HEAD")
4. static final File BRANCHES_HEAD_FOLDER = Utils.join(GITLET_DIR, refs, heads)
5. static final File STAGING_FOLDER = Utils.join(GITLET_DIR, "staging")
6. static final File COMMITS_FOLDER = Utils.join(GITLET_DIR, objects, commits)
7. static final File BLOBS_FOLDER = Utils.join(GITLET_DIR, objects, blobs)
8. static Branch currentBranch
9. static StagingArea stagingArea

### Staging area (Serializable)

Main purpose of an object of this class is to add or remove files to/from staging area. 
Staged files will be stored as **Blobs** in **HashMap** with **key=blob.name**, 
**value=blob.SHA1**. If same file is added twice, old staging area blob must be deleted.

#### Fields

1. private HashMap<String, String> addBlobs
2. private HashMap<String, String> removeBlobs

### Branch

This class will be used to find branches and their last commits

#### Fields

1. private String name
2. private String lastCommit

### Commit (Serializable)

This class will be used to load current commit and create next commit, it will track
it's parent commit and blobs of that parent. New commit is created using data from
current commit and blobs from **Staging Area**.

#### Fields

1. private String name
2. private Date date
3. private String parentCommitID
4. private HashMap<String, String> trackedFiles
5. private string id;

### Blob (Comparable, Serializable)

A Blob object just stores name of a file, and it's SHA1 that will be 
actually used to store it in File System. If name and SHA1 are equal then blobs are equal

#### Fields

1. private String Name
2. private String Content
3. private String SHA1

## Algorithms

### Main

No methods, just a **switch statement** with and **args check**

### Repository 

1. Setup Persistance

    Create all folders and files required

2. Save State

   Saves current branch and staging area state

3. Change HEAD pointer

4. Init
    
    Creates folders' structure or throws an error if exists. Uses **Commit** empty 
    constructor to create initial commit. Uses **Branch**
    to create Master branch with HEAD at the initial commit, points **HEAD**
    to Master's head.


2. Add

    Uses **Blob** to create a new blob, throws an error if no file with that name, 
    then uses **Staging Area** to add the new blob to staging area. 


3. Commit
    
    Uses **Commit** constructor to create and save new commit. **Branch** current
    commit and **Staging Area** state should be passed to that constructor. 
    Updates **Branch** lastCommit


4. Rm

    Uses **Staging Area** to remove file with such name from staging or throw an
    error if file wasn't staged/doesn't exist in current commit/wrong name


5. Log

    Uses **Commit**'s log method to display log


6. Global-log

    Loops through all **Branch**es and use their log() method


7. Find

    Use **Commit**'s find(commitId) method to display all matching commits

8. Status

    Display **Branch** and **Stage Area** status using their toString() methods.


9. Checkout

    Uses **Branch**/**Commit** checkOut() method with given args depending on
    number and form of args provided


10. Branch

    Uses **Branch** constructor with given name to create a new branch


11. Rm branch

    Uses **Branch** remove() method with given name


12. Reset

    Uses **Commit** reset() method with given commitId, **Staging Area** clean()
    method


13. Merge

### Staging Area (Serializable)

1. Read current state
2. Update staging area with new blob
3. Setters/getters
4. Clear
5. To String
6. Save


### Branch

1. Constructor with name 
2. Setters/getters
3. Get last commit by id
4. Static checkout method
5. Static remove method
6. To String
7. Save - saves pointer to last commit in file with branch name
8. From File - loads from pointer
9. Read current branch

### Commit (Serializable)

1. Empty constructor for initial commit
2. Constructor with message, staging area and parentId to create new commit
3. Save
4. From File
6. Setters/getters
7. log
8. Static find
10. Static checkoutFromCommit with commitId and file name

### Blob (Comparable, Serializable)

1. Constructor with File parameter
2. Setters/getters
3. Save
4. From File
5. Equals
6. Delete

## Persistence

### What is need to be saved/loaded?

1. Current state of staging area (blobs in add and rm zones)
2. Current Branch
3. Current Commit
4. All blobs

### How it's going to be saved?

1. Staging area object will be saved as File with HashMap of file name and blob id
2. Branches will be saved as File in ref directory. File name will be a branch name
and id of the last commit will be inside that File
3. Commits are saved as Files in objects/commits dir. Name of the File is commits
id and data, message and pointers to the blobs are inside
4. Blobs are saved as Files in objects/blobs dir. Name is blob id and all file
content is inside

### Folders structure
CWD
    .gitlet
        
        -refs
            
            -heads
        
            -objects
            
                -commits
            
                -blobs
        
        HEAD

        STAGING
