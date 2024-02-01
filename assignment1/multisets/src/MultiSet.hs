module MultiSet (
    MSet(..),
    empty,
    fromList,
    add,
    addMany,
    occs,
    elems,
    subeq,
    union,
    mapMSet
) where

-- Define the MSet data type representing a multiset
data MSet a = MS [(a, Int)] deriving (Show)

-- Constructors

-- Creates an empty multiset
empty :: MSet a
empty = MS []

-- Creates an MSet from a list
fromList :: (Eq a) => [a] -> MSet a
fromList lis = foldl add empty lis

-- Operations

-- Adds an element to the multiset a given amount of times
addMany :: (Eq a) => MSet a -> a -> Int -> MSet a
addMany (MS []) v times = MS [(v, times)]
addMany (MS ((x, occ):xs)) v times
    | v == x    = MS ((x, occ+times):xs)
    | otherwise = let MS rest = addMany (MS xs) v times in MS ((x, occ):rest)

-- Adds an element to the multiset
add :: (Eq a) => MSet a -> a -> MSet a
add mset v = addMany mset v 1

-- Counts the occurrences of an element in the multiset
occs :: (Eq a) => MSet a -> a -> Int
occs (MS []) _ = 0
occs (MS ((x, occ):xs)) v
  | x == v    = occ
  | otherwise = occs (MS xs) v

-- Extracts the elements from the multiset
elems :: MSet a -> [a]
elems (MS xs) = map fst xs

-- Checks if one multiset is a subset of another with at least the same multiplicity
subeq :: (Eq a) => MSet a -> MSet a -> Bool
subeq (MS []) _ = True
subeq (MS ((x, n):xs)) otherset = n <= occs otherset x && subeq (MS xs) otherset

-- Performs the union of two multisets
union :: (Eq a) => MSet a -> MSet a -> MSet a
union (MS xs) (MS ys) = MS (merge xs ys)
    where merge [] ys' = ys'
          merge (x:xs') ys' = merge xs' (addPair x ys')
          addPair (x, occx) [] = [(x, occx)]
          addPair (x, occx) ((y, occy):ys')
            | x == y    = (y, occx + occy) : ys'
            | otherwise = (y, occy) : addPair (x, occx) ys'

mapMSet :: Eq a => (t -> a) -> MSet t -> MSet a
mapMSet f (MS xs) = foldl (\acc (x, n) -> addMany acc (f x) n) empty xs

-- Two multisets are equal if they contain the same elements with the same multiplicity, regardless of the order.
instance Eq a => Eq (MSet a) where
  (MS xs) == (MS ys) = equivMultisets xs ys
    where
      equivMultisets [] [] = True
      equivMultisets [] _  = False
      equivMultisets _ []  = False
      equivMultisets ((x,nx):xs) ys =
        case lookup x ys of
          Just ny -> nx == ny && equivMultisets xs (remove x ys)
          Nothing -> False
      remove _ [] = []
      remove x ((y,ny):ys)
        | x == y = ys
        | otherwise = (y,ny) : remove x ys

instance Foldable MSet where
    foldr f acc (MS xs) = foldr (\(x, _) z -> f x z) acc xs -- apply the folding function f to each element x while ignoring its multiplicity

{-
The mapMSet function is not sufficient to implement the fmap function for the MSet type constructor, 
thus making it impossible to define an instance of Functor using mapMSet alone.
We have
  fmap :: (a -> b) -> f a -> f b
  mapMSet :: Eq a => (t -> a) -> MSet t -> MSet a

Moreover, mapMSet requires that type a must be an instance of Eq, whereas the signature of fmap from 
the Functor type class is more general and does not include such a constraint.
-}