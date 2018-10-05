import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { Motif } from './motif.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<Motif>;

@Injectable()
export class MotifService {

    private resourceUrl =  SERVER_API_URL + 'api/motifs';

    constructor(private http: HttpClient) { }

    create(motif: Motif): Observable<EntityResponseType> {
        const copy = this.convert(motif);
        return this.http.post<Motif>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(motif: Motif): Observable<EntityResponseType> {
        const copy = this.convert(motif);
        return this.http.put<Motif>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<Motif>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<Motif[]>> {
        const options = createRequestOption(req);
        return this.http.get<Motif[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<Motif[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: Motif = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<Motif[]>): HttpResponse<Motif[]> {
        const jsonResponse: Motif[] = res.body;
        const body: Motif[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to Motif.
     */
    private convertItemFromServer(motif: Motif): Motif {
        const copy: Motif = Object.assign({}, motif);
        return copy;
    }

    /**
     * Convert a Motif to a JSON which can be sent to the server.
     */
    private convert(motif: Motif): Motif {
        const copy: Motif = Object.assign({}, motif);
        return copy;
    }
}
