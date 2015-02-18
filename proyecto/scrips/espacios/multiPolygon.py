import utm
from shapely.geometry import Point
from shapely.geometry import MultiPolygon
from shapely.geometry import Polygon
from shapely.wkt import dumps, loads

fich = open("espacio.csv","r")
fich2 = open("geometry.txt","w")

fich.readline()
#fich2.write(fich.readline())

aux = fich.readlines()
cont = 0

for senda in aux :
	init = senda.split("MULTIPOLYGON (((")[0]
	cont += 1
	#print cont
	aux2 = "MULTIPOLYGON (((" + senda.split("\"MULTIPOLYGON (((")[1]
	aux2 = aux2.split(")))\",")[0] + ")))"
	fin = "\"," + senda.split("MULTIPOLYGON (((")[1].split(")))\",")[1]

	objeto = MultiPolygon(loads(aux2))
	#print "cantidad de poligonos = " + str(len(objeto))

	aux6 = []

	for polygon in objeto :
		aux5 = []
		#print len(list(line.coords))
		aux3 = []
		for coordenada in list(polygon.exterior.coords) :
			aux3.append(utm.to_latlon(coordenada[0], coordenada[1], 30, 'T'))
		aux5.append(aux3)
		#print aux3
		#print "cantidad de huecos = " + str(len(list(polygon.interiors)))
		#print list(polygon.interiors)
		aux8 = []
		for hole in list(polygon.interiors) :
			aux3 = []
			for coordenada in list(hole.coords) :
				aux3.append(utm.to_latlon(coordenada[0], coordenada[1], 30, 'T'))
			aux8.append(aux3)
		
		aux5.append(aux8)
		aux6.append(aux5)		
		#print polygon
		"""
		for coordenada in list(polygon.coords) :
			aux3.append(utm.to_latlon(coordenada[0], coordenada[1], 30, 'T'))
		aux4 = LineString(aux3)
		aux5.append(list(aux4.coords))
		"""
	#print aux5

	aux7 = MultiPolygon(aux6)
	#print aux7
	#fich2.write(init+str(aux7)+fin)
	fich2.write("\""+str(aux7)+"\"\n")

	#print aux6

fich.close()
fich2.close()
	
