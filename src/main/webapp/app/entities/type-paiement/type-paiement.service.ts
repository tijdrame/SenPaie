import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { TypePaiement } from './type-paiement.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<TypePaiement>;

@Injectable()
export class TypePaiementService {

    private resourceUrl =  SERVER_API_URL + 'api/type-paiements';

    constructor(private http: HttpClient) { }

    create(typePaiement: TypePaiement): Observable<EntityResponseType> {
        const copy = this.convert(typePaiement);
        return this.http.post<TypePaiement>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(typePaiement: TypePaiement): Observable<EntityResponseType> {
        const copy = this.convert(typePaiement);
        return this.http.put<TypePaiement>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<TypePaiement>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<TypePaiement[]>> {
        const options = createRequestOption(req);
        return this.http.get<TypePaiement[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<TypePaiement[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: TypePaiement = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<TypePaiement[]>): HttpResponse<TypePaiement[]> {
        const jsonResponse: TypePaiement[] = res.body;
        const body: TypePaiement[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to TypePaiement.
     */
    private convertItemFromServer(typePaiement: TypePaiement): TypePaiement {
        const copy: TypePaiement = Object.assign({}, typePaiement);
        return copy;
    }

    /**
     * Convert a TypePaiement to a JSON which can be sent to the server.
     */
    private convert(typePaiement: TypePaiement): TypePaiement {
        const copy: TypePaiement = Object.assign({}, typePaiement);
        return copy;
    }
}
