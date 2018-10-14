import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { DetailPret } from './detail-pret.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<DetailPret>;

@Injectable()
export class DetailPretService {

    private resourceUrl =  SERVER_API_URL + 'api/detail-prets';

    constructor(private http: HttpClient) { }

    create(detailPret: DetailPret): Observable<EntityResponseType> {
        const copy = this.convert(detailPret);
        return this.http.post<DetailPret>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(detailPret: DetailPret): Observable<EntityResponseType> {
        const copy = this.convert(detailPret);
        return this.http.put<DetailPret>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<DetailPret>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<DetailPret[]>> {
        const options = createRequestOption(req);
        return this.http.get<DetailPret[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<DetailPret[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: DetailPret = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<DetailPret[]>): HttpResponse<DetailPret[]> {
        const jsonResponse: DetailPret[] = res.body;
        const body: DetailPret[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to DetailPret.
     */
    private convertItemFromServer(detailPret: DetailPret): DetailPret {
        const copy: DetailPret = Object.assign({}, detailPret);
        return copy;
    }

    /**
     * Convert a DetailPret to a JSON which can be sent to the server.
     */
    private convert(detailPret: DetailPret): DetailPret {
        const copy: DetailPret = Object.assign({}, detailPret);
        return copy;
    }
}
