.PHONY: all test run_unit_test clean TestMSet.hs

TestMSet.hs:
	ghc TestMSet.hs -odir out -hidir out -isrc

test: TestMSet.hs
	./TestMSet

unit_test:
	ghc test/unit_test.hs -odir out -hidir out -isrc && mv test/unit_test .

run_unit_test: unit_test
	./unit_test

clear:
	rm -rf out || true
	rm unit_test || true
	rm TestMSet || true