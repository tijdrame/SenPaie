import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { Categorie } from './categorie.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<Categorie>;

@Injectable()
export class CategorieService {

    private resourceUrl =  SERVER_API_URL + 'api/categories';

    constructor(private http: HttpClient) { }

    create(categorie: Categorie): Observable<EntityResponseType> {
        const copy = this.convert(categorie);
        return this.http.post<Categorie>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(categorie: Categorie): Observable<EntityResponseType> {
        const copy = this.convert(categorie);
        return this.http.put<Categorie>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<Categorie>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<Categorie[]>> {
        const options = createRequestOption(req);
        return this.http.get<Categorie[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<Categorie[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: Categorie = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<Categorie[]>): HttpResponse<Categorie[]> {
        const jsonResponse: Categorie[] = res.body;
        const body: Categorie[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to Categorie.
     */
    private convertItemFromServer(categorie: Categorie): Categorie {
        const copy: Categorie = Object.assign({}, categorie);
        return copy;
    }

    /**
     * Convert a Categorie to a JSON which can be sent to the server.
     */
    private convert(categorie: Categorie): Categorie {
        const copy: Categorie = Object.assign({}, categorie);
        return copy;
    }
}
