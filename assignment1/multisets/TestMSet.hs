import Data.Char (toLower)
import Data.List (sort)
import MultiSet

root_dir = "./aux_files/"

-- Compute the ciao of a string
ciao :: [Char] -> [Char]
ciao str = sort (map toLower str)

-- Read the file and create a MSet made of the ciao of the words
readMSet :: FilePath -> IO (MSet [Char])
readMSet filePath = do
    content <- readFile filePath
    let wordsList = map ciao (words content)
    return (fromList wordsList)

-- Write MSet's elements in the file, one per line, in the format “<elem> - <multiplicity>”.
writeMSet :: FilePath -> MSet [Char] -> IO ()
writeMSet filePath (MS mset) =
    writeFile filePath (unlines $ map (\(x,occ) -> "<"++ x ++"> - <" ++ show occ ++">") mset)

main :: IO()
main = do
    -- load test files into four multisets
    m1 <- readMSet $ root_dir++"anagram.txt";
    m2 <- readMSet $ root_dir++"anagram-s1.txt";
    m3 <- readMSet $ root_dir++"anagram-s2.txt";
    m4 <- readMSet $ root_dir++"margana2.txt";
    
    -- check the 2 facts
    if (m1 /= m4) && ((sort (elems m1)) == (sort (elems m4)))
        then putStrLn "Fact 1 | true: Multisets m1 and m4 are not equal, but they have the same elements"
        else putStrLn "Fact 1 | false: Error! <-----";
    
    if m1 == (union m2 m3)
        then putStrLn "Fact 2 | true: Multiset m1 is equal to the union of multisets m2 and m3"
        else putStrLn "Fact 2 | false: Error! <-----";

    -- write m1 and m4 to file
    writeMSet "./anag-out.txt" m1
    writeMSet "./gana-out.txt" m4