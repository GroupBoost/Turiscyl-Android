import utm
from shapely.geometry import Point
from shapely.geometry import MultiLineString
from shapely.geometry import LineString
from shapely.wkt import dumps, loads

fich = open("original.csv","r")
fich2 = open("destino.csv","w")

#fich.readline()
fich2.write(fich.readline())

aux = fich.readlines()
cont = 0

for senda in aux :
	init = senda.split("MULTILINESTRING ")[0]
	cont += 1
	aux2 = senda.split("MULTILINESTRING ")[1]
	aux2 = aux2.split("))\",")[0] + "))"
	fin = "\"," + senda.split("MULTILINESTRING ")[1].split("))\",")[1]
	aux2 = "MULTILINESTRING "+aux2

	objeto = MultiLineString(loads(aux2))


	aux5 = []

	for line in objeto :
		#print len(list(line.coords))
		aux3 = []
		for coordenada in list(line.coords) :
			aux3.append(utm.to_latlon(coordenada[0], coordenada[1], 30, 'T'))
		aux4 = LineString(aux3)
		aux5.append(list(aux4.coords))
	#print aux5

	aux6 = MultiLineString(aux5)
	fich2.write(init+str(aux6)+fin)
	#fich2.write("\""+str(aux6)+"\"\n")

	#print aux6

fich.close()
fich2.close()
	
