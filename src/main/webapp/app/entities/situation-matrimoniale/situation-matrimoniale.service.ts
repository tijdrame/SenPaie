import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { SituationMatrimoniale } from './situation-matrimoniale.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<SituationMatrimoniale>;

@Injectable()
export class SituationMatrimonialeService {

    private resourceUrl =  SERVER_API_URL + 'api/situation-matrimoniales';

    constructor(private http: HttpClient) { }

    create(situationMatrimoniale: SituationMatrimoniale): Observable<EntityResponseType> {
        const copy = this.convert(situationMatrimoniale);
        return this.http.post<SituationMatrimoniale>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(situationMatrimoniale: SituationMatrimoniale): Observable<EntityResponseType> {
        const copy = this.convert(situationMatrimoniale);
        return this.http.put<SituationMatrimoniale>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<SituationMatrimoniale>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<SituationMatrimoniale[]>> {
        const options = createRequestOption(req);
        return this.http.get<SituationMatrimoniale[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<SituationMatrimoniale[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: SituationMatrimoniale = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<SituationMatrimoniale[]>): HttpResponse<SituationMatrimoniale[]> {
        const jsonResponse: SituationMatrimoniale[] = res.body;
        const body: SituationMatrimoniale[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to SituationMatrimoniale.
     */
    private convertItemFromServer(situationMatrimoniale: SituationMatrimoniale): SituationMatrimoniale {
        const copy: SituationMatrimoniale = Object.assign({}, situationMatrimoniale);
        return copy;
    }

    /**
     * Convert a SituationMatrimoniale to a JSON which can be sent to the server.
     */
    private convert(situationMatrimoniale: SituationMatrimoniale): SituationMatrimoniale {
        const copy: SituationMatrimoniale = Object.assign({}, situationMatrimoniale);
        return copy;
    }
}
