import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { JhiDateUtils } from 'ng-jhipster';

import { Absence } from './absence.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<Absence>;

@Injectable()
export class AbsenceService {

    private resourceUrl =  SERVER_API_URL + 'api/absences';

    constructor(private http: HttpClient, private dateUtils: JhiDateUtils) { }

    create(absence: Absence): Observable<EntityResponseType> {
        const copy = this.convert(absence);
        return this.http.post<Absence>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(absence: Absence): Observable<EntityResponseType> {
        const copy = this.convert(absence);
        return this.http.put<Absence>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<Absence>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<Absence[]>> {
        const options = createRequestOption(req);
        return this.http.get<Absence[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<Absence[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: Absence = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<Absence[]>): HttpResponse<Absence[]> {
        const jsonResponse: Absence[] = res.body;
        const body: Absence[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to Absence.
     */
    private convertItemFromServer(absence: Absence): Absence {
        const copy: Absence = Object.assign({}, absence);
        copy.dateAbsence = this.dateUtils
            .convertLocalDateFromServer(absence.dateAbsence);
        copy.dateCreated = this.dateUtils
            .convertLocalDateFromServer(absence.dateCreated);
        return copy;
    }

    /**
     * Convert a Absence to a JSON which can be sent to the server.
     */
    private convert(absence: Absence): Absence {
        const copy: Absence = Object.assign({}, absence);
        copy.dateAbsence = this.dateUtils
            .convertLocalDateToServer(absence.dateAbsence);
        copy.dateCreated = this.dateUtils
            .convertLocalDateToServer(absence.dateCreated);
        return copy;
    }
}
