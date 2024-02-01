import MultiSet

-- Print test result
printTest :: String -> Bool -> IO ()
printTest msg True  = putStrLn $ "✅ " ++ msg ++ " Passed"
printTest msg False  = putStrLn $ "❌ -----> " ++ msg ++ " Failed <-----"

-- Define test cases
-- Test empty multisets are equal
testEqEmpty :: Bool
testEqEmpty = (empty :: MSet Int) == (empty :: MSet Int)

-- Test multisets equality with the same elements and multiplicities
testEqSame :: Bool
testEqSame = (MS [(1, 2), (2, 3)]) == (MS [(1, 2), (2, 3)])

-- Test multisets equality with different elements
testEqDifferentElements :: Bool
testEqDifferentElements = (MS [(1, 2), (2, 3)]) /= (MS [(1, 2), (3, 3)])

-- Test multisets equality with different multiplicities
testEqDifferentMultiplicities :: Bool
testEqDifferentMultiplicities = (MS [(1, 2), (2, 3)]) /= (MS [(1, 3), (2, 3)])

-- Test adding a new element
testAddNewElement :: Bool
testAddNewElement = add (MS [(1, 1)]) 2 == MS [(1, 1), (2, 1)]

-- Test adding an existing element increases its multiplicity
testAddExistingElement :: Bool
testAddExistingElement = add (MS [(1, 1), (2, 1)]) 1 == MS [(1, 2), (2, 1)]

-- Test adding an existing element multiple times
testAddMultipleTimes :: Bool
testAddMultipleTimes = add (MS [(1, 1), (2, 1)]) 1 == MS [(1, 2), (2, 1)]

-- Test adding many elements to an empty multiset
testAddManyEmpty :: Bool
testAddManyEmpty = addMany (empty) 2 3 == MS [(2, 3)]

-- Test adding many elements to a non-empty multiset
testAddManyNonEmpty :: Bool
testAddManyNonEmpty = addMany (MS [(1, 1)]) 2 2 == MS [(1, 1), (2, 2)]

-- Test adding many elements to an existing element
testAddManyExistingElement :: Bool
testAddManyExistingElement = addMany (MS [(1, 1)]) 1 2 == MS [(1, 3)]

-- Test mapping over an empty multiset
testMapMSetEmpty :: Bool
testMapMSetEmpty = mapMSet (\x -> x + 1) (empty) == empty

-- Test mapping over a non-empty multiset
testMapMSetNonEmpty :: Bool
testMapMSetNonEmpty = mapMSet (\x -> x + 1) (MS [(1, 1), (2, 2)]) == MS [(2, 1), (3, 2)]

-- Test occs with an empty multiset
testOccsEmpty :: Bool
testOccsEmpty = occs (empty) 1 == 0

-- Test occs with a non-empty multiset containing the specified element
testOccsNonEmpty :: Bool
testOccsNonEmpty = occs (MS [(1, 2), (2, 3), (3, 1)]) 2 == 3

-- Test occs with a non-existing element in the multiset
testOccsNonExistingElement :: Bool
testOccsNonExistingElement = occs (MS [(1, 2), (2, 3), (3, 1)]) 4 == 0

-- Test elems with an empty multiset
testElemsEmpty :: Bool
testElemsEmpty = elems (empty :: MSet Int) == []

-- Test elems with a non-empty multiset
testElemsNonEmpty :: Bool
testElemsNonEmpty = elems (MS [(1, 2), (2, 3), (3, 1)]) == [1, 2, 3]

-- Test subeq with empty multisets
testSubeqEmpty :: Bool
testSubeqEmpty = subeq (empty :: MSet Int) (empty :: MSet Int) == True

-- Test subeq with empty multiset and non-empty multiset
testSubeqEmptyNonEmpty :: Bool
testSubeqEmptyNonEmpty = subeq (empty) (MS [(1, 2), (2, 3)]) == True

-- Test subeq with equal non-empty multisets
testSubeqEqual :: Bool
testSubeqEqual = subeq (MS [(1, 2), (2, 3)]) (MS [(1, 2), (2, 3)]) == True

-- Test subeq with non-empty multiset and its subset
testSubeqSubset :: Bool
testSubeqSubset = subeq (MS [(1, 2), (2, 3)]) (MS [(1, 1), (2, 2)]) == False

-- Test subeq with non-empty multiset and its superset
testSubeqSuperset :: Bool
testSubeqSuperset = subeq (MS [(1, 1), (2, 2)]) (MS [(1, 2), (2, 3)]) == True

-- Test union with empty multisets
testUnionEmpty :: Bool
testUnionEmpty = union (empty :: MSet Int) (empty :: MSet Int) == empty

-- Test union with one empty multiset and one non-empty multiset
testUnionOneEmpty :: Bool
testUnionOneEmpty = union (empty) (MS [(1, 2), (2, 3)]) == MS [(1, 2), (2, 3)]

-- Test union with two non-empty multisets
testUnionNonEmpty :: Bool
testUnionNonEmpty = union (MS [(1, 2), (2, 3)]) (MS [(2, 1), (3, 2)]) == MS [(1, 2), (2, 4), (3, 2)]

-- Test foldr with an empty multiset
testFoldrEmpty :: Bool
testFoldrEmpty = foldr (+) 0 (empty :: MSet Int) == 0

-- Test foldr with a non-empty multiset
testFoldrNonEmpty :: Bool
testFoldrNonEmpty = foldr (+) 0 (MS [(1, 2), (2, 3)]) == 3

-- Test fromList with empty list
testFromListEmptyList :: Bool
testFromListEmptyList = fromList ([] :: [Int]) == MS []

-- Test fromList with a single element list
testFromListSingleElement :: Bool
testFromListSingleElement = fromList [1] == MS [(1, 1)]

-- Test fromList with a list of multiple elements
testFromListMultipleElements :: Bool
testFromListMultipleElements = fromList [1, 2, 3] == MS [(1, 1), (2, 1), (3, 1)]

-- Test fromList with a list having repeated elements
testFromListRepeatedElements :: Bool
testFromListRepeatedElements = fromList [1, 2, 2, 3, 3, 3] == MS [(1, 1), (2, 2), (3, 3)]


-- Run tests
main :: IO ()
main = do
    putStrLn "Running 30 tests..."
    printTest "Eq Empty" testEqEmpty
    printTest "Eq Same" testEqSame
    printTest "Eq Different Elements" testEqDifferentElements
    printTest "Eq Different Multiplicities" testEqDifferentMultiplicities
    printTest "Add New Element" testAddNewElement
    printTest "Add Existing Element" testAddExistingElement
    printTest "Add Multiple Times" testAddMultipleTimes
    printTest "AddMany Empty" testAddManyEmpty
    printTest "AddMany Non-Empty" testAddManyNonEmpty
    printTest "AddMany Existing Element" testAddManyExistingElement
    printTest "MapMSet Empty" testMapMSetEmpty
    printTest "MapMSet Non-Empty" testMapMSetNonEmpty
    printTest "Occs Empty" testOccsEmpty
    printTest "Occs Non-Empty" testOccsNonEmpty
    printTest "Occs Non-Existing Element" testOccsNonExistingElement
    printTest "Elems Empty" testElemsEmpty
    printTest "Elems Non-Empty" testElemsNonEmpty
    printTest "Subeq Empty" testSubeqEmpty
    printTest "Subeq Empty and Non-Empty" testSubeqEmptyNonEmpty
    printTest "Subeq Equal" testSubeqEqual
    printTest "Subeq Subset" testSubeqSubset
    printTest "Subeq Superset" testSubeqSuperset
    printTest "Union Empty" testUnionEmpty
    printTest "Union One Empty" testUnionOneEmpty
    printTest "Union Non-Empty" testUnionNonEmpty
    printTest "Foldr Empty" testFoldrEmpty
    printTest "Foldr Non-Empty" testFoldrNonEmpty
    printTest "FromList Empty List" testFromListEmptyList
    printTest "FromList Single Element" testFromListSingleElement
    printTest "FromList Multiple Elements" testFromListMultipleElements
    printTest "FromList Repeated Elements" testFromListRepeatedElements
